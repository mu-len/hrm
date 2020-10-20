package com.jdbc.hrm.Dao.Impl;

import com.jdbc.hrm.Bean.Job;
import com.jdbc.hrm.Bean.Page;
import com.jdbc.hrm.Dao.JobDao;
import com.jdbc.hrm.Util.DButil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JobDaoImpl extends DButil<Job> implements JobDao {
    @Override
    public Job findJob(String name) {
        String sql="select * from job_inf where name=?";
        List<Job> selects = selects(sql, name);
        if(selects.size()>0){
            return selects.get(0);
        }
        return null;
    }

    @Override
    public List<Job> findAllJob() {
        String sql="select * from job_inf";
        List<Job> selects = selects(sql);
        if(selects.size()>0){
            return selects;
        }
        return null;
    }

    @Override
    public boolean addJob(String name, String remark, String state) {
        String sql="insert into job_inf(name,remark,state) values(?,?,?)";
        boolean updates = updates(sql, name, remark, state);
        return updates;
    }

    @Override
    public Page<Job> findPage(int pageNow,String name) {
        int i=0;
        Page<Job> page=new Page<>();
        List<Object> obj =new ArrayList<>();
        page.setPageSize(4);
        page.setPageIndex(pageNow);
        String sql="select * from job_inf where 1=1 ";
        if(null!=name){
            name="%"+name+"%";
            sql=sql+"and name like ? ";
            obj.add(name);
        }
        page.setTotalRecordSum(selects(sql,obj.toArray()).size());
        sql=sql+"limit ?,?";
        obj.add((pageNow-1)*page.getPageSize());
        obj.add(page.getPageSize());
        List<Job> selects = selects(sql, obj.toArray());
        page.setList(selects);
        return page;
    }

    @Override
    public Job findJob(int id) {
        String sql="select * from job_inf where id=?";
        List<Job> selects = selects(sql, id);
        if(selects.size()>0){
            return selects.get(0);
        }
        return null;
    }

    @Override
    public boolean delJob(int id) {
        String sql="delete from job_inf where id=?";
        boolean updates = updates(sql, id);
        return false;
    }

    @Override
    public boolean updateJob(int id, String name, String remark) {
        String sql="update job_inf set name=?,remark=? where id=?";
        boolean updates = updates(sql, name, remark, id);
        return updates;
    }

    @Override
    public Job getEmtity(ResultSet rs) throws SQLException {
        Job job=new Job();
        job.setId(rs.getInt(1));
        job.setName(rs.getString(2));
        job.setRemark(rs.getString(3));
        job.setState(rs.getString(4));
        return job;
    }
}
