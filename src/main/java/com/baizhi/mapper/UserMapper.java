package com.baizhi.mapper;

import com.baizhi.entity.Province;
import com.baizhi.entity.User;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserMapper extends Mapper<User> {
    public List<Province> queryCity();

    public Integer queryUserCount(Integer day);

    /*public void updateUser(User user);*/
}
