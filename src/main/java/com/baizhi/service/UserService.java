package com.baizhi.service;

import com.baizhi.entity.Province;
import com.baizhi.entity.User;
import com.baizhi.entity.UserDTO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public interface UserService {

    public UserDTO queryAll(Integer page, Integer rows);

    public List<Province> distribution();

    public List<Integer> queryCount();

    //导出
    public void outExcel(HttpServletResponse response, HttpSession session);

    //导入
    public void inserExcel(MultipartFile file);

    //注册
    public void registUser(User user, MultipartFile file);

    //登录
    public Object userLogin(String phone, String pass);

    //查询其他用户
    public Object queryOtherUser(Integer uid);

    //注册第一步
    public Object registerOne(String phone, String password);

    //修改个人信息
    public Object changeMess(User user);

    //发送验证码
    public String sendCode(String phone, HttpSession session);

    //比对验证码
    public String compareCode(String phone, String code, HttpSession session);


}
