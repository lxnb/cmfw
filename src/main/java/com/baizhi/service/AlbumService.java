package com.baizhi.service;

import com.baizhi.entity.Album;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface AlbumService {
    public List<Album> queryAlbum();

    public void insertAldum(HttpSession session, MultipartFile file, Album album);

    public List<Album> queryOneAlbum(String id);
}
