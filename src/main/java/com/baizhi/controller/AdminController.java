package com.baizhi.controller;

import com.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("Admin")
public class AdminController {

    @Autowired
    private AdminService service;

    @RequestMapping(value = "login", produces = "text/plain;charset=utf-8")
    public String adminLogin(String name, String password, HttpSession session) {
        try {
            service.adminLogin(name, password, session);
            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

}
