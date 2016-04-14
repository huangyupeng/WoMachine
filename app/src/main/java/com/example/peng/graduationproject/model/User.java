package com.example.peng.graduationproject.model;

/**
 * Created by peng on 2016/4/9.
 */
public class User {

    //用户编号
    private String number;

    //用户手机号码
    private String id;

    //用户姓名
    private String name;

    //用户登录密码
    private String password;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
