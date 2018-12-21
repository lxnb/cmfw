package com.baizhi.controller;

import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("Chapter")
public class ChapterController {
    @Autowired
    private ChapterService service;

    @RequestMapping("insertChapter")
    public String insertChapter(HttpSession session, MultipartFile file, Chapter chapter) {
        try {
            service.insertChapter(session, file, chapter);
            System.out.println("dawdawdw" + chapter.getAlbumId());
            service.insertChapter(session, file, chapter);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
