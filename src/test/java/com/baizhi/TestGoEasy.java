package com.baizhi;

import com.alibaba.fastjson.JSON;
import com.baizhi.entity.Province;
import com.baizhi.entity.User;
import com.baizhi.mapper.UserMapper;
import io.goeasy.GoEasy;
import io.goeasy.publish.GoEasyError;
import io.goeasy.publish.PublishListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestGoEasy {
    @Autowired
    private UserMapper mapper;

    @Test
    public void GoEasy() {
        User user = new User(null, "15465214789", "123456", "adwd", "啦啦啦", "4.jpg", "用户11号", "喇嘛喇嘛", 1, "宁夏", "银川", 1, new Date(), 1);
        mapper.insert(user);
        List<Province> provinces = mapper.queryCity();
        String s = JSON.toJSONString(provinces);
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-f85983491bd943b0b09ca386f90fd862");
        goEasy.publish("140", s, new PublishListener() {
            public void onSuccess() {
                System.out.println("成功");
            }

            public void onFailed(GoEasyError error) {
                System.out.print("消息发布失败 ,  错误编码：" + error.getCode());
            }
        });
    }
}
