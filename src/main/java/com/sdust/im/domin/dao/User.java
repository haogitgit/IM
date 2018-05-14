package com.sdust.im.domin.dao;
/*
 * @author luhao
 * @date 2018/5/13 13:22
 *
 */

import java.util.List;

public class User {

    private String accountId;

    private String name;

    private String email;

    private String birth;

    private String phone;

    private String province;

    private String city;

    private String county;

    private String sex;

    private String password;

    private List<String> homeplace;

    public List<String> getHomeplace() {
        return homeplace;
    }

    public void setHomeplace(List<String> homeplace) {
        this.homeplace = homeplace;
    }

    public User() {

    }

    public  String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
