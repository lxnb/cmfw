package com.baizhi.service;

import com.baizhi.entity.Chapter;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

public interface ChapterService {

    public void insertChapter(HttpSession session, MultipartFile file, Chapter chapter);
}