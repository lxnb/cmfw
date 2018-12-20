package com.baizhi.controller;

import com.baizhi.entity.Banner;
import com.baizhi.entity.BannerDTO;
import com.baizhi.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("Banner")
public class BannerController {

    @Autowired
    private BannerService service;

    @RequestMapping("queryAll")
    public BannerDTO queryAllBanner(Integer page, Integer rows) {
        BannerDTO bannerDTO = service.queryAll(page, rows);
        return bannerDTO;
    }

    @RequestMapping("update")
    public void update(Banner banner) {
        service.update(banner);
    }

    @RequestMapping("delete")
    public void delete(Integer id) {
        service.delete(id);
    }

    @RequestMapping("insert")
    public String insert(String title, String description, HttpSession session, MultipartFile file) {
        try {
            service.insert(title, description, session, file);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }
}
