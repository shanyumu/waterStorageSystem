package com.veyit.waterstoragesystem.entity.po;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String name;
    private String phone;
    private String address;
    private Integer sex;
    private String password;
    private Integer role;
}
