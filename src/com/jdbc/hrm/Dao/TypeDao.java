package com.jdbc.hrm.Dao;

import com.jdbc.hrm.Bean.Page;
import com.jdbc.hrm.Bean.Type;

import java.util.List;

public interface TypeDao {
    List<Type> findAll();
    Type findOne(int id);
    Type findOne(String name);
    Page<Type> findPage(int pageNow,String name);
    boolean addType(String name,int user_id);
    boolean delType(int id);
    boolean updateType(int id,String name);
}
