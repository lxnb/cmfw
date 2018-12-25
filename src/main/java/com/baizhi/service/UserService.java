package com.baizhi.service;

import com.baizhi.entity.Province;
import com.baizhi.entity.User;

import java.util.List;

public interface UserService {

    public List<User> queryAll();

    public List<Province> distribution();

    public List<Integer> queryCount();

}
