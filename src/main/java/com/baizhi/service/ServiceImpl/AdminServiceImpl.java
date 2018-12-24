package com.baizhi.service.ServiceImpl;

import com.baizhi.entity.Admin;
import com.baizhi.mapper.AdminMapper;
import com.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper mapper;

    @Override
    public void adminLogin(String name, String password, HttpSession session, String code) {
        Admin admin = new Admin();
        admin.setName(name);
        Admin select = mapper.selectOne(admin);
        try {
            if (!session.getAttribute("code").equals(code.toLowerCase())) throw new RuntimeException("验证码错误");
            if (select == null) throw new RuntimeException("用户名不存在");
            if (!select.getPassword().equals(password)) throw new RuntimeException("密码输入错误");
            session.setAttribute("admin", select);
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

    }


}
