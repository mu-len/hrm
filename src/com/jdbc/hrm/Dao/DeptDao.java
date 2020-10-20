package com.jdbc.hrm.Dao;

import com.jdbc.hrm.Bean.Dept;
import com.jdbc.hrm.Bean.Page;

import java.util.List;

public interface DeptDao {
    Dept findDept(String name);
    List<Dept> findAllDept();
    boolean addDept(String name,String remark,String state);
    Page<Dept> findPage(int pageNow,String name);
    Dept findDept(int id);
    boolean updateDept(int id,String name,String remark);
    boolean delDate(int id);
}
