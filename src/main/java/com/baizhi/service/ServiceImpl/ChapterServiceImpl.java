package com.baizhi.service.ServiceImpl;

import com.baizhi.entity.Chapter;
import com.baizhi.mapper.ChapterMapper;
import com.baizhi.service.ChapterService;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.MultimediaInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Date;

@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    private ChapterMapper mapper;

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
            Encoder encoder = new Encoder();
            long s = 0;
            MultimediaInfo m;
            try {
                m = encoder.getInfo(files);
                s = m.getDuration();
                istime = s / 60000 + "分" + (s / 1000 - s / 60000 * 60) + "秒！";
                System.out.println("此视频时长为:" + s / 60000 + "分" + (s / 1000 - s / 60000 * 60) + "秒！");
            } catch (EncoderException e) {
                e.printStackTrace();
            }
            chapter.setId(null);
            chapter.setSize(size);
            chapter.setDuration(istime);
            chapter.setUrl("myradio" + "/" + time + "-" + file.getOriginalFilename());
            chapter.setUploadDate(new Date());
            mapper.insert(chapter);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
