package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("Album")
public class AlbumController<p> {
    @Autowired
    private AlbumService service;

    @RequestMapping("queryAlbum")
    public List<Album> queryAlbum() {
        List<Album> albums = service.queryAlbum();
        return albums;
    }

    @RequestMapping("insertAlbum")
    public String insertAlbum(HttpSession session, MultipartFile file, Album album) {
        try {
            service.insertAldum(session, file, album);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
