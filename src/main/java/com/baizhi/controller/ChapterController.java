package com.baizhi.controller;

import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("Chapter")
public class ChapterController {
    @Autowired
    private ChapterService service;

    @RequestMapping("insertChapter")
    public String insertChapter(HttpSession session, MultipartFile file, Chapter chapter) {
        try {
            service.insertChapter(session, file, chapter);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @RequestMapping("downLoad")
    public String downLoad(HttpSession session, HttpServletResponse response, String url, String title) {
        try {
            service.downLoad(session, response, url, title);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @RequestMapping("queryChapter")
    public List<Chapter> queryChapter(String test) {
        System.out.println("传入值" + test);
        List<Chapter> list = service.queryChapter(test);
        return list;
    }
}
