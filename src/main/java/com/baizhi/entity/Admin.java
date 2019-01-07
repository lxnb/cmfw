package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    private Integer aId;
    private String name;
    private String password;
    private String salt;
    private List<Role> role;

    public Admin(String name) {
        this.name = name;
    }
}
