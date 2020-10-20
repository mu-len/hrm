package com.jdbc.hrm.Dao;

import com.jdbc.hrm.Bean.Emp;
import com.jdbc.hrm.Bean.Page;

import java.util.Date;
import java.util.List;

public interface EmpDao {
    List<Emp> findAllEmp();
    Page<Emp> findPage(int pageNow, Object...obj);
    boolean addEmp(String name, String card_id, String address, String post_code, String tel, String phone, String qq_num, String email, int sex, String party, String birthday, String race, String education, String speciality, String hobby, String remark, Date create_date, int state, int dept_id, int job_id);
    Emp findEmp(int id);
    boolean updateDept(Emp emp);
    Emp findCardId(String cardId);
    boolean delEmp(int id);
}
