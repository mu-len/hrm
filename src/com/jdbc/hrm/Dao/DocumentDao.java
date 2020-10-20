package com.jdbc.hrm.Dao;

import com.jdbc.hrm.Bean.Document;
import com.jdbc.hrm.Bean.Page;

import java.util.List;

public interface DocumentDao {
    List<Document> findAll();
    Document findOne(int id);
    Page<Document> findPage(int pageNow,String title);
    boolean updateDocument(int id,String title,String filename,String filetype,String filebytes,String remark);
    boolean addDocument(String tirle,String filename,String filetype,String filebytes,String remark,int user_id);
    boolean delDocument(int id);
}
