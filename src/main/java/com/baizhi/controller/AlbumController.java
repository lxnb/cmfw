package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.entity.AlbumDTO;
import com.baizhi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("Album")
public class AlbumController<p> {
    @Autowired
    private AlbumService service;

    @RequestMapping("queryAlbum")
    public AlbumDTO queryAlbum(Integer page, Integer rows) {
        AlbumDTO albums = service.queryAlbum(page, rows);
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


    @RequestMapping("queryOneAlbum")
    public List<Album> queryOneAlbum(String id) {
        List<Album> album = service.queryOneAlbum(id);
        System.out.println(album);
        return album;
    }

    @RequestMapping("outExcel")
    public void outExcel(HttpServletResponse response, HttpSession session) {
        service.outExcel(response, session);
    }


    //首页
    @RequestMapping(value = "queryHomePage", produces = "text/plain;charset=utf-8")
    public Object queryHomePage(Integer uid, String type, String sub_type) {
        Object o = service.queryHomePage(uid, type, sub_type);
        return o;
    }

    //闻详情
    @RequestMapping(value = "queryWenMess", produces = "text/plain;charset=utf-8")
    public Object queryWenMess(Integer uid, String albumId) {
        Object o = service.queryWenMess(uid, albumId);
        return o;
    }
}
