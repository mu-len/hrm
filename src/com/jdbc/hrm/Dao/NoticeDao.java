package com.jdbc.hrm.Dao;

import com.jdbc.hrm.Bean.Notice;
import com.jdbc.hrm.Bean.Page;

import java.util.Date;

public interface NoticeDao {
    Notice findOne(int id);
    Notice finOne(String name);
    Page<Notice> findPage(int pageNow,String name);
    boolean addNotice(String name, int typeId, String content,int userId);
    boolean updateNotice(int id,String name,int typeId,String content);
    boolean delNotice(int id);
}
