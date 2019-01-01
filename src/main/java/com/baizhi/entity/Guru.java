package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Guru implements Serializable {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    private String dharma;
    private String headPic;
    private Integer status;
}
