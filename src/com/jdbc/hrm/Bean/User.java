package com.jdbc.hrm.Bean;

import java.util.Date;

public class User {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String loginname;
    private String password;
    private int status;
    private Date createDate;
    private String username;

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginName) {
        this.loginname = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", loginName='" + loginname + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", createDate='" + createDate + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
