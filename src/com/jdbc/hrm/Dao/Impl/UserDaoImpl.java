package com.jdbc.hrm.Dao.Impl;

import com.jdbc.hrm.Bean.Page;
import com.jdbc.hrm.Bean.User;
import com.jdbc.hrm.Dao.UserDao;
import com.jdbc.hrm.Util.DButil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDaoImpl extends DButil<User> implements UserDao {
    @Override
    public User findUser(String loginName, String password) {//根据loginname和password查询数据是否存在，存在则封装对象返回
        String sql="select * from user_inf where loginName=? and password=?";
        List<User> selects = selects(sql, loginName, password);
        if(selects.size()>0){
            return selects.get(0);
        }
        return null;
    }

    @Override
    public List<User> findAllUser() {//查询user_inf表的所有数据，并封装对象返回
        String sql="select * from user_inf";
        List<User> selects = selects(sql);
        if(selects.size()>0){
            return selects;
        }
        return null;
    }

    @Override
    public boolean addUser(String loginName, String password, int status, Date createDate, String username) {
        String sql="insert into user_inf(loginName,password,status,createDate,username) values(?,?,?,?,?)";
        boolean updates = updates(sql, loginName, password, status, createDate, username);
        return updates;
    }

    @Override
    public Page<User> findPage(int pageNow,String loginName,String username,String state) {
        Page<User> page=new Page<>();
        List<Object> obj =new ArrayList<>();
        String loginNames=null;
        String usernames=null;
        int states=0;
        page.setPageSize(4);
        page.setPageIndex(pageNow);
        String sql="select * from user_inf where 1=1 ";
        if(null!=loginName){
            loginNames="%"+loginName+"%";
            sql=sql+"and loginName like ? ";
            obj.add(loginNames);
        }
        if(null!=username){
            usernames="%"+username+"%";
            sql=sql+"and username like ? ";
            obj.add(usernames);
        }
        if(null!=state){
            states=Integer.parseInt(state);
            sql=sql+"and status=? ";
            obj.add(states);
        }
        page.setTotalRecordSum(selects(sql,obj.toArray()).size());
        sql=sql+"limit ?,?";
        obj.add((pageNow - 1) * page.getPageSize());
        obj.add(page.getPageSize());
        List<User> selects = selects(sql,obj.toArray());
        page.setList(selects);
        return page;
    }

    @Override
    public boolean delId(int id) {
        String sql="delete from user_inf where id=?";
        boolean updates = updates(sql, id);
        return updates;
    }

    @Override
    public User findLogin(String loginname) {
        String sql="select * from user_inf where loginname=?";
        List<User> selects = selects(sql, loginname);
        if(selects.size()>0){
            return selects.get(0);
        }
        return null;
    }

    @Override
    public User findId(int id) {
        String sql="select * from user_inf where id=?";
        List<User> selects = selects(sql, id);
        if(selects.size()>0){
            return selects.get(0);
        }
        return null;
    }

    @Override
    public boolean updateUser(int id, String loginname, String password, int status, String username) {
        String sql="update user_inf set loginname=?,password=?,status=?,username=? where id=?";
        boolean updates = updates(sql, loginname, password, status, username, id);
        return updates;
    }

    @Override
    public User getEmtity(ResultSet rs) throws SQLException {
        User user=new User();
        user.setId(rs.getInt(1));
        user.setLoginname(rs.getString(2));
        user.setPassword(rs.getString(3));
        user.setStatus(rs.getInt(4));
        user.setCreateDate(rs.getDate(5));
        user.setUsername(rs.getString(6));
        return user;
    }
}
