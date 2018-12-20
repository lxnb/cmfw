package com.baizhi.service.ServiceImpl;

import com.baizhi.entity.Menu;
import com.baizhi.mapper.MenuMapper;
import com.baizhi.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper mapper;

    @Override
    public List<Menu> queryMenu() {
        List<Menu> menus = mapper.queryMenu();
        return menus;
    }
}
