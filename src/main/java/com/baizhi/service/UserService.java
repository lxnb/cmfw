package com.baizhi.service;

import com.baizhi.entity.Province;
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


}
