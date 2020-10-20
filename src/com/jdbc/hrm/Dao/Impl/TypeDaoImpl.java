package com.jdbc.hrm.Dao.Impl;

import com.jdbc.hrm.Bean.Page;
import com.jdbc.hrm.Bean.Type;
import com.jdbc.hrm.Dao.TypeDao;
import com.jdbc.hrm.Util.DButil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TypeDaoImpl extends DButil<Type> implements TypeDao {
    @Override
    public List<Type> findAll() {
        String sql="select * from type_inf";
        List<Type> selects = selects(sql);
        if(selects.size()>0){
            return selects;
        }
        return null;
    }

    @Override
    public Type findOne(int id) {
        String sql="select * from type_inf where id=?";
        List<Type> selects = selects(sql, id);
        if(selects.size()>0){
            return selects.get(0);
        }
        return null;
    }

    @Override
    public Type findOne(String name) {
        String sql="select * from type_inf where name=?";
        List<Type> selects = selects(sql, name);
        if(selects.size()>0){
            return selects.get(0);
        }
        return null;
    }

    @Override
    public Page<Type> findPage(int pageNow, String name) {
        Page<Type> page=new Page<>();
        List<Object> obj=new ArrayList<>();
        page.setPageSize(4);
        page.setPageIndex(pageNow);
        String sql="select * from type_inf where 1=1 ";
        if(null!=name){
            name="%"+name+"%";
            sql=sql+"and name like ? ";
            obj.add(name);
        }
        page.setTotalRecordSum(selects(sql,obj.toArray()).size());
        sql=sql+"limit ?,?";
        obj.add((pageNow-1)*page.getPageSize());
        obj.add(page.getPageSize());
        List<Type> selects = selects(sql, obj.toArray());
        page.setList(selects);
        return page;
    }

    @Override
    public boolean addType(String name,int user_id) {
        String sql="insert into type_inf(name,create_date,state,user_id,modify_date) values(?,?,?,?,?)";
        boolean updates = updates(sql, name, new Date(), 0, user_id, new Date());
        return updates;
    }

    @Override
    public boolean delType(int id) {
        String sql="delete from type_inf where id=?";
        boolean updates = updates(sql, id);
        return updates;
    }

    @Override
    public boolean updateType(int id,String name) {
        String sql="update type_inf set name=? where id=?";
        boolean updates = updates(sql, name,id);
        return updates;
    }

    @Override
    public Type getEmtity(ResultSet rs) throws SQLException {
        UserDaoImpl udi=new UserDaoImpl();
        Type type=new Type();
        type.setId(rs.getInt(1));
        type.setName(rs.getString(2));
        type.setCreateDate(rs.getDate(3));
        type.setState(rs.getString(4));
        type.setUser(udi.findId(rs.getInt(5)));
        type.setModifyDate(rs.getDate(6));
        return type;
    }
}
