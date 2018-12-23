package com.baizhi.service.ServiceImpl;

import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import com.baizhi.mapper.AlbumMapper;
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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
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
            System.out.println(descFile);
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
                //获得文件信息
                m = encoder.getInfo(files);
                //获取音频长度
                s = m.getDuration();
                istime = s / 60000 + "分" + (s / 1000 - s / 60000 * 60) + "秒";
                System.out.println("此视频时长为:" + s / 60000 + "分" + (s / 1000 - s / 60000 * 60) + "秒");
            } catch (EncoderException e) {
                e.printStackTrace();
            }
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
    public String downLoad(HttpSession session, HttpServletResponse response, String url) {
        //文件夹所在路径
        String realPath = session.getServletContext().getRealPath("/myradio");
        //只包含文件名
        String fileName = url.split("/")[2];
        System.out.println(fileName);
        //文件绝对路径
        String path = realPath + "\\" + url.split("/")[2];
        System.out.println(path);
        File file = new File(realPath, fileName);
        FileInputStream fis = null;
        BufferedInputStream bis = null;

        try {
            //文件下载时所携带的文件名（设置响应头，以附件形式）
            response.setHeader("content-disposition", "attachment;filename" + URLEncoder.encode(fileName, "UTF-8"));
            byte[] buffer = new byte[1024];
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
