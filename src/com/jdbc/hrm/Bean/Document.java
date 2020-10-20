package com.jdbc.hrm.Bean;

import java.util.Date;

public class Document {
    private int id;
    private String title;
    private String filename;
    private String filetype;
    private String filebytes;
    private String remark;
    private Date createDate;
    private User user;

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", filename='" + filename + '\'' +
                ", filetype='" + filetype + '\'' +
                ", filebytes='" + filebytes + '\'' +
                ", remark='" + remark + '\'' +
                ", createDate=" + createDate +
                ", user=" + user +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    public String getFilebytes() {
        return filebytes;
    }

    public void setFilebytes(String filebytes) {
        this.filebytes = filebytes;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
