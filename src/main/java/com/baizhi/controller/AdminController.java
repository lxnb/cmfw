package com.baizhi.controller;

import com.baizhi.service.AdminService;
import com.baizhi.util.CreateValidateCode;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
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
    public String adminLogin(String name, String password, HttpSession session, String enCode) {
        Subject subject = SecurityUtils.getSubject();
        AuthenticationToken token = new UsernamePasswordToken(name, password);
        try {
            service.adminLogin(session, enCode);
            subject.login(token);
            return null;
        } catch (UnknownAccountException e) {
            return "账号错误";
        } catch (IncorrectCredentialsException e) {
            return "密码错误";
        } catch (Exception e) {
            return "验证码错误";
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

    @RequestMapping("loginout")
    @ResponseBody
    public String loginOut() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return null;
    }
}


