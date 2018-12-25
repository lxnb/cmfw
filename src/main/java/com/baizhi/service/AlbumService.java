package com.baizhi.service;

import com.baizhi.entity.Album;
import com.baizhi.entity.AlbumDTO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public interface AlbumService {
    public AlbumDTO queryAlbum(Integer page, Integer rows);

    public void insertAldum(HttpSession session, MultipartFile file, Album album);

    public List<Album> queryOneAlbum(String id);

    public void outExcel(HttpServletResponse response, HttpSession session);
}
