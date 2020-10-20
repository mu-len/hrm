package com.gec.hrm.servlet;

import com.jdbc.hrm.Bean.Emp;
import com.jdbc.hrm.Bean.Page;
import com.jdbc.hrm.Dao.Impl.DeptDaoImpl;
import com.jdbc.hrm.Dao.Impl.EmpDaoImpl;
import com.jdbc.hrm.Dao.Impl.JobDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet({"/employeelist.action","/updateEmployees","/addEmployee","/employeelists.action","/getcardid.action","/employeedel.action"})
public class SelectEmpServlet extends HttpServlet {
    EmpDaoImpl edi=new EmpDaoImpl();//emp功能实现类
    JobDaoImpl jdi=new JobDaoImpl();//job功能实现类
    DeptDaoImpl ddi=new DeptDaoImpl();//dept功能实现类
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();//获取地址的后缀连接字符串
        String action = uri.substring(uri.lastIndexOf("/") + 1);
        HttpSession session=request.getSession();//声明session
        int pageIndex=1;//声明分页起始页数
        if(action.equals("employeelist.action")){//employeelist.action用于初始页面加载或者分页变化时的回应请求
            String pageIndex1 = request.getParameter("pageIndex");
            String job_id = request.getParameter("job_id");
            String name = request.getParameter("name");
            String cardId = request.getParameter("cardId");
            if(null==cardId&&null==name&&null==job_id){//第一次请求页面未加载时获取的值为null，除此之外为""或者其他值
                session.setAttribute("job_id",null);//如果是第一次加载设置对应的session，值都为null
                session.setAttribute("name",null);
                session.setAttribute("cardId",null);
                session.setAttribute("sex",null);
                session.setAttribute("phone",null);
                session.setAttribute("dept_id",null);
            }else {//否则页面不是第一次加载，表单已经初始化，判断表单提交的pageIndex是否为""，不是""就将分页起始数pageIndex的值设置为提交过来的值
                if(!"".equals(pageIndex1)){
                    pageIndex=Integer.parseInt(pageIndex1);
                }
            }
        }else if(action.equals("updateEmployees")){
            Emp emp=new Emp();//创建emp对象
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//时间格式化
            emp.setId(Integer.parseInt(request.getParameter("id").trim()));
            emp.setName(request.getParameter("name").trim());
            emp.setCardId(request.getParameter("cardId").trim());
            emp.setSex(Integer.parseInt(request.getParameter("sex").trim()));
            emp.setJob(jdi.findJob(Integer.parseInt(request.getParameter("job_id"))));
            emp.setEducation(request.getParameter("education").trim());
            emp.setEmail(request.getParameter("email").trim());
            emp.setPhone(request.getParameter("phone").trim());
            emp.setTel(request.getParameter("tel").trim());
            emp.setParty(request.getParameter("party").trim());
            emp.setQqNum(request.getParameter("qqNum").trim());
            emp.setAddress(request.getParameter("address").trim());
            emp.setPostCode(request.getParameter("postCode").trim());
            try {
                emp.setBirthday(sdf.parse(request.getParameter("birthday").trim()));//将字符串转为date
            } catch (ParseException e) {
                e.printStackTrace();
            }
            emp.setRace(request.getParameter("race").trim());
            emp.setSpeciality(request.getParameter("speciality").trim());
            emp.setHobby(request.getParameter("hobby").trim());
            emp.setRemark(request.getParameter("remark").trim());
            emp.setDept(ddi.findDept(Integer.parseInt(request.getParameter("dept_id"))));
            boolean b = edi.updateDept(emp);
            if(b){//返回是否修改成功的信息
                request.setAttribute("massage","修改成功");
            }else {
                request.setAttribute("massage","修改失败");
            }
        }else if(action.equals("addEmployee")){//addEmployee用于添加数据行
            String cardId = request.getParameter("cardId");
            Emp emp = edi.findCardId(cardId);
            if(null==emp){//判断请求的cardId是否已存在
                String name = request.getParameter("name").trim();
                int sex = Integer.parseInt(request.getParameter("sex"));
                int job_id = Integer.parseInt(request.getParameter("job_id"));
                String education = request.getParameter("education").trim();
                String email = request.getParameter("email").trim();
                String phone = request.getParameter("phone").trim();
                String tel = request.getParameter("tel").trim();
                String party = request.getParameter("party").trim();
                String qqNum = request.getParameter("qqNum").trim();
                String address = request.getParameter("address").trim();
                String postCode = request.getParameter("postCode").trim();
                String birthday = request.getParameter("birthday").trim();
                String race = request.getParameter("race").trim();
                String speciality = request.getParameter("speciality").trim();
                String hobby = request.getParameter("hobby").trim();
                String remark = request.getParameter("remark").trim();
                int dept_id = Integer.parseInt(request.getParameter("dept_id"));
                boolean b = edi.addEmp(name, cardId, address, postCode, tel, phone, qqNum, email, sex, party, birthday, race, education, speciality, hobby, remark, new Date(), 0, dept_id, job_id);
                if(b){//返回是否添加成功的信息
                    request.setAttribute("massage","添加成功");
                }else {
                    request.setAttribute("massage","添加失败");
                }
            }else {//若员工cardId已存在则不进行添加操作，直接返回信息
                request.setAttribute("massage","该员工身份已存在");
            }
        }else if(action.equals("employeelists.action")){//employeelists.action用于用户点击查询时获取到查询表单提交的值，并将session覆盖
            session.setAttribute("job_id",request.getParameter("job_id").trim().equals("")?null:request.getParameter("job_id"));
            session.setAttribute("name",request.getParameter("name").trim().equals("")?null:request.getParameter("name"));
            session.setAttribute("cardId",request.getParameter("cardId").trim().equals("")?null:request.getParameter("cardId"));
            session.setAttribute("sex",request.getParameter("sex").trim().equals("")?null:request.getParameter("sex"));
            session.setAttribute("phone",request.getParameter("phone").trim().equals("")?null:request.getParameter("phone"));
            session.setAttribute("dept_id",request.getParameter("dept_id").trim().equals("")?null:request.getParameter("dept_id"));
        }else if(action.equals("getcardid.action")){//ajax判断cardId是否已存在
            String cardId = doDate(request);//调用方法获取请求过来的数据，进行拆分
            cardId=cardId.substring(11,cardId.length()-2);
            Emp emp = edi.findCardId(cardId);
            PrintWriter writer= response.getWriter();
            if(null!=emp){
                writer.print(false);
            }else {
                writer.print(true);
            }
            writer.close();
            return;
        }else if(action.equals("employeedel.action")){//employeedel.action用于删除数据行
            String[] employeeIds = request.getParameterValues("employeeIds");
            if(employeeIds.length>0){
                for(String strId:employeeIds){
                    int i = Integer.parseInt(strId);
                    edi.delEmp(i);
                }
            }
        }
        //以上连接后台处理完成后都会重新进行返回数据和跳转，跳转位置都为WEB-INF/jsp/employee/employeelist.jsp
        Page<Emp> page=edi.findPage(pageIndex,session.getAttribute("job_id"),session.getAttribute("name"),session.getAttribute("cardId"),session.getAttribute("sex"),session.getAttribute("phone"),session.getAttribute("dept_id"));
        request.setAttribute("jobList",jdi.findAllJob());//初始化job select下拉列表
        request.setAttribute("deptList",ddi.findAllDept());//初始化dept select下拉列表
        request.setAttribute("employeelist",page.getList());
        request.setAttribute("pageModel",page);
        request.getRequestDispatcher("WEB-INF/jsp/employee/employeelist.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);//所有请求都在doPost进行处理
    }
    private String doDate(HttpServletRequest request){
        StringBuffer buffer=new StringBuffer();//字符串缓冲区
        try {
            BufferedReader reader = request.getReader();
            String json=null;
            while ((json=reader.readLine())!=null){
                buffer.append(json);//将获取到的流数据写都buffer
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString();//返回字符串
    }
}
