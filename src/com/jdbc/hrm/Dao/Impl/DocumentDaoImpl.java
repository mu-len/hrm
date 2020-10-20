package com.jdbc.hrm.Dao.Impl;

import com.jdbc.hrm.Bean.Document;
import com.jdbc.hrm.Bean.Page;
import com.jdbc.hrm.Dao.DocumentDao;
import com.jdbc.hrm.Util.DButil;

import javax.naming.event.ObjectChangeListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DocumentDaoImpl extends DButil<Document> implements DocumentDao {
    @Override
    public List<Document> findAll() {
        String sql="select * from document_inf";
        List<Document> selects = selects(sql);
        if(selects.size()>0){
            return selects;
        }
        return null;
    }

    @Override
    public Document findOne(int id) {
        String sql="select * from document_inf where id=?";
        List<Document> selects = selects(sql, id);
        if(selects.size()>0){
            return selects.get(0);
        }
        return null;
    }

    @Override
    public Page<Document> findPage(int pageNow,String title) {
        Page<Document> page=new Page<>();
        List<Object> obj=new ArrayList<>();
        page.setPageSize(4);
        page.setPageIndex(pageNow);
        String sql="select * from document_inf where 1=1 ";
        if(null!=title){
            title="%"+title+"%";
            sql=sql+"and title like ? ";
            obj.add(title);
        }
        page.setTotalRecordSum(selects(sql,obj.toArray()).size());
        sql=sql+"limit ?,?";
        obj.add((pageNow-1)*page.getPageSize());
        obj.add(page.getPageSize());
        List<Document> selects = selects(sql, obj.toArray());
        page.setList(selects);
        return page;
    }

    @Override
    public boolean updateDocument(int id, String title, String filename, String filetype, String filebytes, String remark) {
        String sql="update document_inf set title=?,filename=?,filetype=?,filebytes=?,remark=? where id=?";
        boolean updates = updates(sql, title, filename, filetype, filebytes, remark, id);
        return updates;
    }

    @Override
    public boolean addDocument(String title, String filename, String filetype, String filebytes, String remark, int user_id) {
        String sql="insert into document_inf(title,filename,filetype,filebytes,remark,create_date,user_id) values(?,?,?,?,?,?,?)";
        boolean updates = updates(sql, title, filename, filetype, filebytes, remark, new Date(), user_id);
        return updates;
    }

    @Override
    public boolean delDocument(int id) {
        String sql="delete from document_inf where id=?";
        boolean updates = updates(sql, id);
        return updates;
    }

    @Override
    public Document getEmtity(ResultSet rs) throws SQLException {
        UserDaoImpl udi=new UserDaoImpl();
        Document document=new Document();
        document.setId(rs.getInt(1));
        document.setTitle(rs.getString(2));
        document.setFilename(rs.getString(3));
        document.setFiletype(rs.getString(4));
        document.setFilebytes(rs.getString(5));
        document.setRemark(rs.getString(6));
        document.setCreateDate(rs.getDate(7));
        document.setUser(udi.findId(rs.getInt(8)));
        return document;
    }
}
