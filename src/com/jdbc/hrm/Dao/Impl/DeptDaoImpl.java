package com.jdbc.hrm.Dao.Impl;

import com.jdbc.hrm.Bean.Dept;
import com.jdbc.hrm.Bean.Page;
import com.jdbc.hrm.Dao.DeptDao;
import com.jdbc.hrm.Util.DButil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DeptDaoImpl extends DButil<Dept> implements DeptDao {

    @Override
    public Dept findDept(String name) {
        String sql="select * from dept_inf where name=?";
        List<Dept> selects = selects(sql, name);
        if(selects.size()>0){
            return selects.get(0);
        }
        return null;
    }

    @Override
    public List<Dept> findAllDept() {
        String sql="select * from dept_inf";
        List<Dept> selects = selects(sql);
        if(selects.size()>0){
            return selects;
        }
        return null;
    }

    @Override
    public boolean addDept(String name, String remark, String state) {
        String sql="insert into dept_inf(name,remark,state) values(?,?,?)";
        boolean updates = updates(sql, name, remark, state);
        return updates;
    }

    @Override
    public Page<Dept> findPage(int pageNow,String name) {
        int i=0;
        Page<Dept> page=new Page<>();
        page.setPageSize(4);
        if(null!=name){
            String sql="select * from dept_inf where name like ?";
            name="%"+name+"%";
            List<Dept> selects = selects(sql, name);
            page.setTotalRecordSum(selects.size());
        }else {
            page.setTotalRecordSum(findAllDept().size());
        }
        page.setPageIndex(pageNow);
        String sql="select * from dept_inf where 1=1 ";
        if(null!=name){
            i=i+1;
            name="%"+name+"%";
            sql=sql+"and name like ? ";
        }
        sql=sql+"limit ?,?";
        if(i==0){
            List<Dept> selects = selects(sql, (pageNow - 1) * page.getPageSize(), page.getPageSize());
            page.setList(selects);
        }else {
            List<Dept> selects = selects(sql, name,(pageNow - 1) * page.getPageSize(), page.getPageSize());
            page.setList(selects);
        }
        return page;
    }

    @Override
    public Dept findDept(int id) {
        String sql="select * from dept_inf where id=?";
        List<Dept> selects = selects(sql, id);
        if(selects.size()>0){
            return selects.get(0);
        }
        return null;
    }

    @Override
    public boolean updateDept(int id,String name,String remark) {
        String sql="update dept_inf set name=?,remark=? where id=?";
        boolean updates = updates(sql, name, remark, id);
        return updates;
    }

    @Override
    public boolean delDate(int id) {
        String sql="delete from dept_inf where id=?";
        boolean updates = updates(sql, id);
        return updates;
    }

    @Override
    public Dept getEmtity(ResultSet rs) throws SQLException {
        Dept dept=new Dept();
        dept.setId(rs.getInt(1));
        dept.setName(rs.getString(2));
        dept.setRemark(rs.getString(3));
        dept.setState(rs.getString(4));
        return dept;
    }
}
