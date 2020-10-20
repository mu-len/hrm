package com.jdbc.hrm.Dao;

import com.jdbc.hrm.Bean.Page;
import com.jdbc.hrm.Bean.User;

import java.util.Date;
import java.util.List;

public interface UserDao {
    User findUser(String loginName,String password);
    List<User> findAllUser();
    boolean addUser(String loginName, String password, int status, Date createDate, String username);
    Page<User> findPage(int pageNow,String loginName,String username,String state);
    boolean delId(int id);
    User findLogin(String loginname);
    User findId(int id);
    boolean updateUser(int id,String loginname,String password,int status,String username);
}
