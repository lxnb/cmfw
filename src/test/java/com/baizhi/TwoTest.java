package com.baizhi;

import com.baizhi.entity.Album;
import com.baizhi.entity.Article;
import com.baizhi.entity.Banner;
import com.baizhi.entity.User;
import com.baizhi.mapper.AlbumMapper;
import com.baizhi.mapper.ArticleMapper;
import com.baizhi.mapper.BannerMapper;
import com.baizhi.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TwoTest {

    @Autowired
    private ArticleMapper mapper;
    @Autowired
    private AlbumMapper mapper2;
    @Autowired
    private BannerMapper mapper3;
    @Autowired
    private UserMapper mapper4;


    @Test
    public void Test1() {
        List<Article> articles = mapper.queryMyGuruText(1);
        for (Article article : articles) {
            System.out.println(article);
        }
    }

    @Test
    public void Test2() {
        List<Article> articles = mapper.queryOtherGuruText(1);
        for (Article article : articles) {
            System.out.println(article);
        }
    }


    @Test
    public void Test3() {
        List<Album> albums = mapper2.queryNewSixAlbum();
        for (Album album : albums) {
            System.out.println(album);
        }
    }

    @Test
    public void Test4() {
        List<Banner> banners = mapper3.queryFiveBanner();
        for (Banner banner : banners) {
            System.out.println(banner);
        }
    }

    @Test
    public void Test5() {
        List<Banner> banners = mapper3.selectAll();
        for (Banner banner : banners) {
            System.out.println(banner);
        }
    }

    @Test
    public void Test6() {
        Album album = mapper2.queryOneAlbum("aa");
        System.out.println(album);
    }

    @Test
    public void Test7() {
        Example example = new Example(User.class);
        example.createCriteria().andNotEqualTo("uId", 1);
        List<User> users = mapper4.selectByExample(example);
        for (User user : users) {
            System.out.println(user);
        }
    }
}
