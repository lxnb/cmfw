package com.baizhi.controller;

import com.baizhi.conf.CreateValidateCode;
import com.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("Admin")
public class AdminController {

    @Autowired
    private AdminService service;

    @RequestMapping(value = "login", produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String adminLogin(String name, String password, HttpSession session, String code) {
        try {
            service.adminLogin(name, password, session, code);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @RequestMapping("code")
    public void CodeImg(HttpSession session, HttpServletResponse response) {
        CreateValidateCode code = new CreateValidateCode();
        String num = code.getCode();
        session.setAttribute("code", num);
        try {
            ServletOutputStream out = response.getOutputStream();
            System.out.println(num);
            code.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


