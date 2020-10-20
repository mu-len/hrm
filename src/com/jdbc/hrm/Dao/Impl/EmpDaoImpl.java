package com.jdbc.hrm.Dao.Impl;

import com.jdbc.hrm.Bean.Emp;
import com.jdbc.hrm.Bean.Job;
import com.jdbc.hrm.Bean.Page;
import com.jdbc.hrm.Dao.EmpDao;
import com.jdbc.hrm.Util.DButil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmpDaoImpl extends DButil<Emp> implements EmpDao {
    DeptDaoImpl ddi=new DeptDaoImpl();
    JobDaoImpl jdi=new JobDaoImpl();
    @Override
    public List<Emp> findAllEmp() {
        String sql="select * from employee_inf";
        List<Emp> selects = selects(sql);
        if(selects.size()>0){
            return selects;
        }
        return null;
    }

    @Override
    public Page<Emp> findPage(int pageNow, Object... obj) {
        List<Object> objs=new ArrayList<>();
        int jobId=0;
        String name=null;
        String card_id;
        int sex=0;
        String phone=null;
        int deptId=0;
        Page<Emp> page=new Page<>();
        page.setPageSize(4);
        page.setPageIndex(pageNow);
        String sql="select * from employee_inf where 1=1 ";
        if(null!=obj[0]){
            jobId=Integer.parseInt((String) obj[0]);
            sql=sql+"and job_id=? ";
            objs.add(jobId);
        }
        if(null!=obj[1]){
            name="%"+obj[1]+"%";
            sql=sql+"and name like ? ";
            objs.add(name);
        }
        if(null!=obj[2]){
            card_id="%"+obj[2]+"%";
            sql=sql+"and card_id like ? ";
            objs.add(card_id);
        }
        if(null!=obj[3]){
            sex=Integer.parseInt((String)obj[3]);
            sql=sql+"and sex=? ";
            objs.add(sex);
        }
        if(null!=obj[4]){
            phone="%"+obj[4]+"%";
            sql=sql+"and phone like ? ";
            objs.add(phone);
        }
        if(null!=obj[5]){
            deptId=Integer.parseInt((String)obj[5]);
            sql=sql+"and phone like ? ";
            objs.add(deptId);
        }
        page.setTotalRecordSum(selects(sql, objs.toArray()).size());
        sql=sql+"limit ?,?";
        objs.add((pageNow-1)*page.getPageSize());
        objs.add(page.getPageSize());
        List<Emp> selects = selects(sql, objs.toArray());
        page.setList(selects);
        return page;
    }

    @Override
    public boolean addEmp(String name, String card_id, String address, String post_code, String tel, String phone, String qq_num, String email, int sex, String party, String birthday, String race, String education, String speciality, String hobby, String remark, Date create_date, int state, int dept_id, int job_id) {
        String sql="insert into employee_inf(name,card_id,address,post_code,tel,phone,qq_num,email,sex,party,birthday,race,education,speciality,hobby,remark,create_date,state,dept_id,job_id) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        boolean updates = updates(sql, name, card_id, address, post_code, tel, phone, qq_num, email, sex, party, birthday, race, education, speciality, hobby, remark, create_date, state, dept_id, job_id);
        return updates;
    }

    @Override
    public Emp findEmp(int id) {
        String sql="select * from employee_inf where id=?";
        List<Emp> selects = selects(sql, id);
        if(selects.size()>0){
            return selects.get(0);
        }
        return null;
    }

    @Override
    public boolean updateDept(Emp emp) {
        String sql="update employee_inf set name=?,card_id=?,address=?,sex=?,job_id=?,education=?,email=?,phone=?,tel=?,party=?,qq_num=?,post_code=?,birthday=?,race=?,speciality=?,hobby=?,remark=?,create_date=?,state=?,dept_id=? where id=?";
        boolean updates = updates(sql, emp.getName(), emp.getCardId(), emp.getAddress(), emp.getSex(), emp.getJob().getId(), emp.getEducation(), emp.getEmail(), emp.getPhone(), emp.getTel(), emp.getParty(), emp.getQqNum(), emp.getPostCode(), emp.getBirthday(), emp.getRace(), emp.getSpeciality(), emp.getHobby(), emp.getRemark(), new Date(), 0, emp.getDept().getId(),emp.getId());
        return updates;
    }

    @Override
    public Emp findCardId(String cardId) {
        String sql="select * from employee_inf where card_id=?";
        List<Emp> selects = selects(sql, cardId);
        if(selects.size()>0){
            return selects.get(0);
        }
        return null;
    }

    @Override
    public boolean delEmp(int id) {
        String sql="delete from employee_inf where id=?";
        boolean updates = updates(sql, id);
        return updates;
    }

    @Override
    public Emp getEmtity(ResultSet rs) throws SQLException {
        Emp emp=new Emp();
        emp.setId(rs.getInt(1));
        emp.setName(rs.getString(2));
        emp.setCardId(rs.getString(3));
        emp.setAddress(rs.getString(4));
        emp.setPostCode(rs.getString(5));
        emp.setTel(rs.getString(6));
        emp.setPhone(rs.getString(7));
        emp.setQqNum(rs.getString(8));
        emp.setEmail(rs.getString(9));
        emp.setSex(rs.getInt(10));
        emp.setParty(rs.getString(11));
        emp.setBirthday(rs.getDate(12));
        emp.setRace(rs.getString(13));
        emp.setEducation(rs.getString(14));
        emp.setSpeciality(rs.getString(15));
        emp.setHobby(rs.getString(16));
        emp.setRemark(rs.getString(17));
        emp.setCreateDate(rs.getDate(18));
        emp.setState(rs.getString(19));
        emp.setDept(ddi.findDept(rs.getInt(20)));
        emp.setJob(jdi.findJob(rs.getInt(21)));
        return emp;
    }
}
