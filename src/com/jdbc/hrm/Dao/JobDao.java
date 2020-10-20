package com.jdbc.hrm.Dao;

import com.jdbc.hrm.Bean.Job;
import com.jdbc.hrm.Bean.Page;

import java.util.List;

public interface JobDao {
    Job findJob(String name);
    List<Job> findAllJob();
    boolean addJob(String name,String remark,String state);
    Page<Job> findPage(int pageNow,String name);
    Job findJob(int id);
    boolean delJob(int id);
    boolean updateJob(int id,String name,String remark);
}
