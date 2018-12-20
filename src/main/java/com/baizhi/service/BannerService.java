package com.baizhi.service;

import com.baizhi.entity.Banner;
import com.baizhi.entity.BannerDTO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

public interface BannerService {
    public BannerDTO queryAll(Integer page, Integer rows);

    public void update(Banner banner);

    public void delete(Integer id);

    public void insert(String title, String description, HttpSession session, MultipartFile file);
}
