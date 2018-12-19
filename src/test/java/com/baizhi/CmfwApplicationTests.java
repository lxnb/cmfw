package com.baizhi;

import com.baizhi.entity.Admin;
import com.baizhi.entity.Menu;
import com.baizhi.entity.User;
import com.baizhi.mapper.AdminMapper;
import com.baizhi.mapper.MenuMapper;
import com.baizhi.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CmfwApplicationTests {

    @Autowired
    private UserMapper mapper;
    @Autowired
    private MenuMapper mapper2;
    @Autowired
    private AdminMapper mapper3;

    @Test
    public void contextLoads() {
        List<User> users = mapper.selectAll();
        System.out.println(users);
    }

    @Test
    public void test1() {
        List<Menu> menus = mapper2.queryMenu();
        System.out.println(menus);
    }

    @Test
    public void test2() {
        Admin admin = new Admin();
        admin.setName("张三");
        List<Admin> select = mapper3.select(admin);
        System.out.println(select);


    }
}

