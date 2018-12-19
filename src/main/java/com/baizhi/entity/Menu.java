package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Menu implements Serializable {
    private Integer mId;
    private String title;
    private String iconcls;
    private String url;
    private List<Menu> menus;
}
