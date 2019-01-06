package com.baizhi.service.ServiceImpl;

import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import com.baizhi.mapper.AlbumMapper;
import com.baizhi.mapper.ChapterMapper;
import com.baizhi.service.ChapterService;
import com.baizhi.util.AudioUtils;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
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
    @Autowired
    FastFileStorageClient fastFileStorageClient;

    @Override
    public void insertChapter(HttpSession session, MultipartFile file, Chapter chapter) {
        File files = null;
        String istime = null;
        try {
            //创建File类型空文件接收MultpartFile类型下file的内容
            files = File.createTempFile("tmp", null);
            //类型转换
            file.transferTo(files);
            FileInputStream stream = new FileInputStream(files);
            //计算时长
            long filesize = files.length();
            //提交至fastdfs
            StorePath storePath = fastFileStorageClient.uploadFile(stream, filesize, FilenameUtils.getExtension(file.getOriginalFilename()), null);
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
            chapter.setUrl(storePath.getGroup() + "/" + storePath.getPath());
            chapter.setUploadDate(new Date());
            mapper.insert(chapter);
            Album album = mapper2.selectByPrimaryKey(chapter.getAlbumId());
            album.setCount(album.getCount() + 1);
            mapper2.updateByPrimaryKey(album);
            stream.close();
            files.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void downLoad(HttpSession session, HttpServletResponse response, String url, String title) {
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
            String[] split = url.split("/", 2);
            byte[] bytes = fastFileStorageClient.downloadFile(split[0], split[1], new DownloadByteArray());
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
