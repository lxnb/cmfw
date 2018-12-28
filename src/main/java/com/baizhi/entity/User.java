package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    @ExcelIgnore
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer uId;
    @Excel(name = "手机号")
    private String phone;
    @Excel(name = "密码")
    private String password;
    @Excel(name = "盐")
    private String salt;
    @Excel(name = "介绍", width = 20)
    private String sign;
    @Excel(name = "头像", width = 20, height = 15, type = 2)
    private String headPic;
    @Excel(name = "名字")
    private String name;
    @Excel(name = "法号")
    private String dharma;
    @Excel(name = "性别")
    private Integer sex;
    @Excel(name = "省份")
    private String province;
    @Excel(name = "城市")
    private String city;
    @Excel(name = "状态")
    private Integer status;
    @Excel(name = "注册时间", width = 20, format = "YYYY年MM月dd日")
    @JSONField(format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date regDate;
}
