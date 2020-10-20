package com.jdbc.hrm.Bean;

import java.util.Date;

public class Notice {
    private int id;
    private String name;
    private Date createDate;
    private Type type;
    private String content;
    private User user;
    private Date modifyDate;

    @Override
    public String toString() {
        return "Notice{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createDate=" + createDate +
                ", typeId='" + type + '\'' +
                ", content='" + content + '\'' +
                ", user=" + user +
                ", modifyDate=" + modifyDate +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
}
