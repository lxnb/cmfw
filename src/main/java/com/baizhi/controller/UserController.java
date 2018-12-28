package com.baizhi.controller;

import com.baizhi.entity.Province;
import com.baizhi.entity.User;
import com.baizhi.entity.UserDTO;
import com.baizhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("User")
public class UserController {
    @Autowired
    private UserService service;

    @RequestMapping("queryAll")
    public UserDTO queryAll(Integer page, Integer rows) {
        UserDTO users = service.queryAll(page, rows);
        return users;
    }

    @RequestMapping("distribution")
    public List<Province> distribution() {
        List<Province> distribution = service.distribution();
        return distribution;
    }

    @RequestMapping("queryUserCount")
    public List<Integer> queryUserCount() {
        List<Integer> integers = service.queryCount();
        return integers;
    }

    @RequestMapping("outExcel")
    public String outExcel(HttpServletResponse response, HttpSession session) {
        try {
            service.outExcel(response, session);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @RequestMapping("inserExcel")
    public String inserExcel(MultipartFile file) {
        try {
            service.inserExcel(file);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @RequestMapping("registUser")
    public void registUser(User user, MultipartFile file) {
        service.registUser(user, file);
    }

}
