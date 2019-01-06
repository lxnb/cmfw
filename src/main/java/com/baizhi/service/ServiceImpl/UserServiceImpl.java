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
import com.baizhi.util.MSGUtils;
import com.baizhi.util.RandomSaltUtil;
import com.github.pagehelper.PageHelper;
import org.apache.commons.codec.digest.DigestUtils;
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
import java.util.Timer;
import java.util.TimerTask;

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

    @Override
    public void registUser(User user, MultipartFile file) {

    }

    @Override
    public Object userLogin(String phone, String pass) {
        if (phone != null && pass != null) {
            User user = mapper.selectOne(new User(phone));
            if (user != null) {
                String salt = user.getSalt();
                String md5 = DigestUtils.md5Hex(pass + salt);
                if (user.getPassword().equals(md5)) {
                    return user;
                } else {
                    return "密码错误";
                }
            } else {
                return "电话号码不存在";
            }
        } else {
            return "参数不能为空";
        }

    }

    @Override
    public Object queryOtherUser(Integer uid) {
        if (uid != null) {
            Example example = new Example(User.class);
            example.createCriteria().andNotEqualTo("uId", 1);
            List<User> users = mapper.selectByExample(example);
            return users;
        } else {
            return "参数不可为空";
        }

    }

    @Override
    public Object registerOne(String phone, String password) {
        if (phone != null && password != null) {
            String salt = RandomSaltUtil.generetRandomSaltCode();
            String md5pass = DigestUtils.md5Hex(password + salt);
            mapper.insert(new User(null, phone, md5pass, salt, 3));
            User user = new User(phone);
            User user1 = mapper.selectOne(user);
            return user1;
        } else {
            return "参数不可为空";
        }

    }

    @Override
    public Object changeMess(User user) {
        if (user.getUId() != null) {
            mapper.updateByPrimaryKeySelective(user);
            User user1 = mapper.selectOne(user);
            return user1;
        } else {
            return "用户id不可为空";
        }
    }


    @Override
    public String sendCode(String phone, HttpSession session) {
        if (phone != null) {
            String s = RandomSaltUtil.generetRandomSaltCode();
            try {
                MSGUtils.AliMSG(phone, s);
                session.setAttribute(phone, s);
                Timer timer = new Timer();
                //设置一个线程去管理手机验证码生命周期1分钟
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        session.removeAttribute(phone);
                        timer.cancel();
                    }
                }, 60 * 1000);
                //设置一个线程去管理用户注册手机号，一小时未填写验证码
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        User user = mapper.selectOne(new User(phone));
                        if (user.getStatus() == 3) {
                            mapper.delete(new User(user.getUId()));
                        }
                    }
                }, 60 * 60 * 1000);
                return "success";
            } catch (Exception e) {
                e.printStackTrace();
                return e.getMessage();
            }
        } else {
            return "参数不可为空";
        }
    }

    @Override
    public String compareCode(String phone, String code, HttpSession session) {
        if (phone != null && code != null) {
            User user = mapper.selectOne(new User(phone));
            if (user != null) {
                String mycode = (String) session.getAttribute(phone);
                if (code.equals(mycode)) {
                    user.setStatus(1);
                    mapper.updateByPrimaryKeySelective(user);
                    session.removeAttribute(phone);
                    return "success";
                } else {
                    return "error";
                }
            } else {
                return "手机号码不正确";
            }
        } else {
            return "error";
        }

    }
}
