package com.baizhi.service.ServiceImpl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.entity.*;
import com.baizhi.mapper.AlbumMapper;
import com.baizhi.mapper.ArticleMapper;
import com.baizhi.mapper.BannerMapper;
import com.baizhi.mapper.UserMapper;
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
import java.util.*;

@Service
@Transactional
public class AlbumServiceIml implements AlbumService {


    @Autowired
    private AlbumMapper mapper;
    @Autowired
    private BannerMapper mapper1;
    @Autowired
    private ArticleMapper mapper2;
    @Autowired
    private UserMapper mapper3;


    @Override
    public AlbumDTO queryAlbum(Integer page, Integer rows) {
        List<Album> albums = mapper.queryAlbum(page, rows);
        Album a = new Album();
        int i = mapper.selectCount(a);
        AlbumDTO dto = new AlbumDTO(albums, i);
        return dto;
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
        List<Album> list = mapper.queryAllAlbum();
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

    @Override
    public Object queryHomePage(Integer uid, String type, String sub_type) {
        if (uid != null || type != null) {
            if (type.equals("all")) {
                Map<String, Object> map = new HashMap<String, Object>();
                List<Album> albums = mapper.queryNewSixAlbum();
                List<Banner> banners = mapper1.queryFiveBanner();
                List<Article> articles = mapper2.queryNewText();
                map.put("albums", albums);
                map.put("banner", banners);
                map.put("articles", articles);
                return map;
            } else if (type.equals("wen")) {
                Map<String, Object> map = new HashMap<String, Object>();
                List<Album> albums = mapper.queryAllAlbum();
                map.put("album", albums);
                return map;
            } else {
                if (sub_type != null) {
                    User user = mapper3.selectByPrimaryKey(uid);
                    Map<String, Object> map = new HashMap<String, Object>();
                    if (sub_type.equals("ssyj")) {
                        List<Article> articles = mapper2.queryMyGuruText(user.getGuruId());
                        map.put("articles", articles);
                        return map;
                    } else {
                        List<Article> articles = mapper2.queryOtherGuruText(user.getGuruId());
                        map.put("articles", articles);
                        return map;
                    }
                } else {
                    return "参数不能为空";
                }
            }
        } else {
            return "参数不能为空";
        }
    }

    @Override
    public Object queryWenMess(Integer uid, String albumId) {
        if (uid != null || albumId != null) {
            Album album = mapper.queryOneAlbum(albumId);
            return album;
        } else {
            return "参数不能为空";
        }
    }
}
