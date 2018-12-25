package com.baizhi.service.ServiceImpl;

import com.baizhi.entity.Province;
import com.baizhi.entity.User;
import com.baizhi.mapper.UserMapper;
import com.baizhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper mapper;


    @Override
    public List<User> queryAll() {
        List<User> users = mapper.selectAll();
        return users;
    }

    @Override
    public List<Province> distribution() {
        List<Province> provinces = mapper.queryCity();
        return provinces;
    }

    @Override
    public List<Integer> queryCount() {
        Integer integer = mapper.queryUserCount(7);
        Integer integer1 = mapper.queryUserCount(14);
        Integer integer2 = mapper.queryUserCount(21);
        List<Integer> list = new ArrayList<Integer>();
        list.add(integer);
        list.add(integer1);
        list.add(integer2);
        return list;
    }
}
