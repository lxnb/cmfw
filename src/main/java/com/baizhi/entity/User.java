package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private Integer uId;
    private String phone;
    private String password;
    private String salt;
    private String sign;
    private String headPic;
    private String name;
    private String dharma;
    private Integer sex;
    private String province;
    private String city;
    private Integer status;
    private Date regDate;
}
