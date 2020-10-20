package com.gec.hrm.servlet;

import com.jdbc.hrm.Bean.Dept;
import com.jdbc.hrm.Bean.Emp;
import com.jdbc.hrm.Bean.Job;
import com.jdbc.hrm.Dao.Impl.DeptDaoImpl;
import com.jdbc.hrm.Dao.Impl.EmpDaoImpl;
import com.jdbc.hrm.Dao.Impl.JobDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet({"/updateEmployee","/employeeadd.action"})
public class UpdateEmpServlet extends HttpServlet {
    EmpDaoImpl edi=new EmpDaoImpl();//emp功能实现类
    JobDaoImpl jdi=new JobDaoImpl();//job功能实现类
    DeptDaoImpl ddi=new DeptDaoImpl();//dept功能实现类
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();//获取地址的后缀连接字符串
        String action = uri.substring(uri.lastIndexOf("/") + 1);
        if(action.equals("updateEmployee")){//跳转到修改数据行页面
            String id = request.getParameter("id");
            int i = Integer.parseInt(id);
            Emp emp = edi.findEmp(i);//找到要进行修改的emp对象
            request.setAttribute("jobs",jdi.findAllJob());//初始化job select下拉列表
            request.setAttribute("depts",ddi.findAllDept());//初始化dept select下拉列表
            request.setAttribute("employee",emp);
            request.getRequestDispatcher("WEB-INF/jsp/employee/employeeedit.jsp").forward(request,response);
        }else if(action.equals("employeeadd.action")){//跳转到添加数据行页面
            request.setAttribute("jobList",jdi.findAllJob());//初始化job select下拉列表
            request.setAttribute("deptList",ddi.findAllDept());//初始化dept select下拉列表
            request.getRequestDispatcher("WEB-INF/jsp/employee/employeeadd.jsp").forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);//所有请求都在doPost进行处理
    }
}
