package com.baizhi.service.ServiceImpl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.entity.Album;
import com.baizhi.mapper.AlbumMapper;
import com.baizhi.service.AlbumService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class AlbumServiceIml implements AlbumService {


    @Autowired
    private AlbumMapper mapper;


    @Override
    public List<Album> queryAlbum() {
        List<Album> albums = mapper.queryAlbum();
        return albums;
    }

    @Override
    public void insertAldum(HttpSession session, MultipartFile file, Album album) {
        try {
            ServletContext servlet = session.getServletContext();
            String realPath = servlet.getRealPath("/myimg");
            //目标文件
            Long time = new Date().getTime();
            File descFile = new File(realPath + "/" + time + "-" + file.getOriginalFilename());
            //上传
            file.transferTo(descFile);
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            album.setId(uuid);
            album.setScore(5);
            album.setPubDate(new Date());
            album.setCount(0);
            album.setCoverImg(realPath + "/" + time + "-" + file.getOriginalFilename());
            mapper.insert(album);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Album> queryOneAlbum(String id) {
        Album album = new Album();
        album.setId(id);
        Album album1 = mapper.selectByPrimaryKey(album);
        List<Album> list = new ArrayList<Album>();
        list.add(album1);
        return list;
    }

    @Override
    public void outExcel(HttpServletResponse response, HttpSession session) {
        List<Album> list = mapper.queryAlbum();
        for (Album album : list) {
            album.setCoverImg(session.getServletContext().getRealPath(album.getCoverImg()));
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("专辑统计", "专辑"),
                Album.class, list);
        try {
            String encode = URLEncoder.encode("专辑详情.xls", "UTF-8");
            response.setHeader("Content-Disposition", "attachment;fileName=" + encode);
            response.setContentType("application/vnd.ms-excel");
            workbook.write(response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
