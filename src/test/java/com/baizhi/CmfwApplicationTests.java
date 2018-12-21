package com.baizhi;

import com.baizhi.entity.*;
import com.baizhi.mapper.*;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.session.RowBounds;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.mybatis.mapper.entity.Example;

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
    @Autowired
    private BannerMapper mapper4;
    @Autowired
    private AlbumMapper mapper5;
    @Autowired
    private ChapterMapper mapper6;

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

    @Test
    public void test3() {
        Admin admin = new Admin();
        admin.setName("张三");
        List<Admin> select = mapper3.select(admin);
        System.out.println(select);
    }

    @Test
    public void test4() {
        Banner banner = new Banner();
        RowBounds bb = new RowBounds(4, 4);
        List<Banner> banners = mapper4.selectByRowBounds(banner, bb);
        System.out.println(banners);
    }

    @Test
    public void test5() {
        Banner banner = new Banner();
        RowBounds bb = new RowBounds(4, 4);
        int i = mapper4.selectCount(banner);
        System.out.println(i);
    }


    @Test
    public void test6() {
        Example em = new Example(Banner.class);
        Banner banner = new Banner();
        em.setOrderByClause("b_id");
        em.createCriteria().andAllEqualTo(banner);
        List<Banner> banners = mapper4.selectByExample(em);
        System.out.println(banners);
    }


    @Test
    public void test8() {
        PageHelper.startPage(1, 2);
        List<Album> albums = mapper5.queryAlbum();
        System.out.println(albums);
    }

    @Test
    public void test9() {

    }
}

