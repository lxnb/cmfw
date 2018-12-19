package com.baizhi.controller;

import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("User")
public class UserController {
    @Autowired
    private UserService service;

    @RequestMapping("queryAll")
    public List<User> queryAll() {
        List<User> users = service.queryAll();
        return users;
    }
}
