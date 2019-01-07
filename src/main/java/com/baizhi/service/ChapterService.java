package com.baizhi.service;

import com.baizhi.entity.Chapter;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public interface ChapterService {
    //上传
    public void insertChapter(HttpSession session, MultipartFile file, Chapter chapter);

    //下载
    public void downLoad(HttpSession session, HttpServletResponse response, String url, String title);

    //搜索引擎查章节
    public List<Chapter> queryChapter(String text);

}