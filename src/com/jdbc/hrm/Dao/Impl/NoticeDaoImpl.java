package com.jdbc.hrm.Dao.Impl;

import com.jdbc.hrm.Bean.Notice;
import com.jdbc.hrm.Bean.Page;
import com.jdbc.hrm.Dao.NoticeDao;
import com.jdbc.hrm.Util.DButil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NoticeDaoImpl extends DButil<Notice> implements NoticeDao {
    @Override
    public Notice findOne(int id) {
        String sql="select * from notice_inf where id=?";
        List<Notice> selects = selects(sql, id);
        if(selects.size()>0){
            return selects.get(0);
        }
        return null;
    }

    @Override
    public Notice finOne(String name) {
        String sql="select * from notice_inf where name=?";
        List<Notice> selects = selects(sql, name);
        if(selects.size()>0){
            return selects.get(0);
        }
        return null;
    }

    @Override
    public Page<Notice> findPage(int pageNow, String name) {
        Page<Notice> page=new Page<>();
        List<Object> obj=new ArrayList<>();
        page.setPageSize(4);
        page.setPageIndex(pageNow);
        String sql="select * from notice_inf where 1=1 ";
        if(null!=name){
            name="%"+name+"%";
            sql =sql+"and name like ? ";
            obj.add(name);
        }
        page.setTotalRecordSum(selects(sql,obj.toArray()).size());
        sql=sql+"limit ?,?";
        obj.add((pageNow-1)*page.getPageSize());
        obj.add(page.getPageSize());
        List<Notice> selects = selects(sql, obj.toArray());
        page.setList(selects);
        return page;
    }

    @Override
    public boolean addNotice(String name, int typeId, String content,int userId) {
        String sql="insert into notice_inf(name,create_date,type_id,content,user_id,modify_date) values(?,?,?,?,?,?)";
        boolean updates = updates(sql, name, new Date(), typeId, content, userId, new Date());
        return updates;
    }

    @Override
    public boolean updateNotice(int id,String name, int typeId, String content) {
        String sql="update notice_inf set name=?,type_id=?,content=?,modify_date=? where id=?";
        boolean updates = updates(sql, name, typeId, content, new Date(), id);
        return updates;
    }

    @Override
    public boolean delNotice(int id) {
        String sql="delete from notice_inf where id=?";
        boolean updates = updates(sql, id);
        return updates;
    }

    @Override
    public Notice getEmtity(ResultSet rs) throws SQLException {
        UserDaoImpl udi=new UserDaoImpl();
        TypeDaoImpl tdi=new TypeDaoImpl();
        Notice notice = new Notice();
        notice.setId(rs.getInt(1));
        notice.setName(rs.getString(2));
        notice.setCreateDate(rs.getDate(3));
        notice.setType(tdi.findOne(rs.getInt(4)));
        notice.setContent(rs.getString(5));
        notice.setUser(udi.findId(rs.getInt(6)));
        notice.setModifyDate(rs.getDate(7));
        return notice;
    }
}
