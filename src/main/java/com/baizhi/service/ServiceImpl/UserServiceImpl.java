package com.baizhi.service.ServiceImpl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.baizhi.entity.Province;
import com.baizhi.entity.User;
import com.baizhi.entity.UserDTO;
import com.baizhi.mapper.UserMapper;
import com.baizhi.service.UserService;
import com.github.pagehelper.PageHelper;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper mapper;


    @Override
    public UserDTO queryAll(Integer page, Integer rows) {
        Example example = new Example(User.class);
        example.orderBy("uId").desc();
        PageHelper.startPage(page, rows);
        List<User> users = mapper.selectByExample(example);
        User u = new User();
        int i = mapper.selectCount(u);
        UserDTO dto = new UserDTO(users, i);
        return dto;
    }

    @Override
    public List<Province> distribution() {
        List<Province> provinces = mapper.queryCity();
        return provinces;
    }

    @Override
    public List<Integer> queryCount() {
        Integer integer = mapper.queryUserCount(7);
        Integer integer1 = mapper.queryUserCount(14);
        Integer integer2 = mapper.queryUserCount(21);
        List<Integer> list = new ArrayList<Integer>();
        list.add(integer);
        list.add(integer1);
        list.add(integer2);
        return list;
    }

    @Override
    public void outExcel(HttpServletResponse response, HttpSession session) {
        List<User> list = mapper.selectAll();
        for (User user : list) {
            user.setHeadPic(session.getServletContext().getRealPath("/myimg/" + user.getHeadPic()));
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("用户统计", "第XX批"),
                User.class, list);
        try {
            String encode = URLEncoder.encode("用户信息.xls", "UTF-8");
            response.setHeader("Content-Disposition", "attachment;fileName=" + encode);
            response.setContentType("application/vnd.ms-excel");
            workbook.write(response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void inserExcel(MultipartFile file) {
        try {
            File newfile = null;
            FileUtils.copyInputStreamToFile(file.getInputStream(), newfile);
            ImportParams params = new ImportParams();
            params.setTitleRows(1);
            params.setHeadRows(1);
            List<User> list = ExcelImportUtil.importExcel(newfile, User.class, params);
            for (User user : list) {
                System.out.println("dawdawdawd" + user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
