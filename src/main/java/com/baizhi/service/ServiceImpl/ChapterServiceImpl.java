package com.baizhi.service.ServiceImpl;

import com.baizhi.conf.AudioUtils;
import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import com.baizhi.mapper.AlbumMapper;
import com.baizhi.mapper.ChapterMapper;
import com.baizhi.service.ChapterService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Date;

@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    private ChapterMapper mapper;
    @Autowired
    private AlbumMapper mapper2;

    @Override
    public void insertChapter(HttpSession session, MultipartFile file, Chapter chapter) {
        File files;
        String istime = null;
        try {
            ServletContext servlet = session.getServletContext();
            String realPath = servlet.getRealPath("/myradio");
            //目标文件
            Long time = new Date().getTime();
            File descFile = new File(realPath + "/" + time + "-" + file.getOriginalFilename());
            //上传
            file.transferTo(descFile);
            //计算文件大小
            files = new File(realPath + "/" + time + "-" + file.getOriginalFilename());
            long filesize = files.length();
            //文件大小变量
            String size = "";
            DecimalFormat df = new DecimalFormat("#.00");
            if (filesize < 1024) {
                size = df.format((double) filesize) + "BT";
            } else if (filesize < 1048576) {
                size = df.format((double) filesize / 1024) + "KB";
            } else if (filesize < 1073741824) {
                size = df.format((double) filesize / 1048576) + "MB";
            } else {
                size = df.format((double) filesize / 1073741824) + "GB";
            }
            //计算音频时长
            istime = AudioUtils.getDuration(files);
            chapter.setId(null);
            chapter.setSize(size);
            chapter.setDuration(istime);
            chapter.setUrl("/myradio/" + time + "-" + file.getOriginalFilename());
            chapter.setUploadDate(new Date());
            mapper.insert(chapter);
            Album album = mapper2.selectByPrimaryKey(chapter.getAlbumId());
            album.setCount(album.getCount() + 1);
            mapper2.updateByPrimaryKey(album);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void downLoad(HttpSession session, HttpServletResponse response, String url, String title) {
        //文件夹路径
        String realPath = session.getServletContext().getRealPath("/");
        //文件路径
        String filePath = realPath + url;
        File file = new File(filePath);
        //获取文件后缀（mp3）
        String extension = FilenameUtils.getExtension(url);
        //为文件拼接类型
        String oldName = title + "." + extension;
        String encode = null;
        try {
            encode = URLEncoder.encode(oldName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setHeader("Content-Disposition", "attachment;fileName=" + encode);
        response.setContentType("audio/mpeg");
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(FileUtils.readFileToByteArray(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
