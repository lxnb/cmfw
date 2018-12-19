package com.baizhi.mapper;

import com.baizhi.entity.Menu;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface MenuMapper extends Mapper<Menu> {
    //一级导航，二级导航
    public List<Menu> queryMenu();
}
