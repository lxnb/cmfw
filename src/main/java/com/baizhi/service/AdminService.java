package com.baizhi.service;

import javax.servlet.http.HttpSession;

public interface AdminService {
    public void adminLogin(String name, String password, HttpSession session);
}
