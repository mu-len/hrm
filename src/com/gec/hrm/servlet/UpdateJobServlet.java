package com.gec.hrm.servlet;

import com.jdbc.hrm.Bean.Job;
import com.jdbc.hrm.Dao.Impl.JobDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"/jobaddJob", "/jobaddsave.action","/viewJob.action"})
public class UpdateJobServlet extends HttpServlet {
    JobDaoImpl jdi=new JobDaoImpl();//job功能实现类
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();//获取地址的后缀连接字符串
        String action = uri.substring(uri.lastIndexOf("/") + 1);
        if(action.equals("jobaddJob")){//跳转到添加数据行页面
            request.getRequestDispatcher("WEB-INF/jsp/job/jobadd.jsp").forward(request,response);
        }else if(action.equals("jobaddsave.action")){//处理添加数据行请求
            String name = request.getParameter("name").trim();
            Job job = jdi.findJob(name);
            if(null==job){//判断请求的jobname是否已经存在
                String remark = request.getParameter("remark").trim();
                boolean b = jdi.addJob(name, remark, "0");
                if(b){
                    request.setAttribute("massage","添加成功");//返回添加是否成功的信息
                }else {
                    request.setAttribute("massage","添加失败");
                }
            }else {//已存在则不进行添加，直接返回信息
                request.setAttribute("massage","已存在该职位");
            }
            request.getRequestDispatcher("WEB-INF/jsp/job/jobadd.jsp").forward(request,response);
        }else if(action.equals("viewJob.action")){//跳转到修改数据行页面
            String id = request.getParameter("id").trim();
            int ids=Integer.parseInt(id);
            Job job = jdi.findJob(ids);//找到要进行修改的job对象
            request.setAttribute("job",job);
            request.getRequestDispatcher("WEB-INF/jsp/job/jobedit.jsp").forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);//所有请求都在doPost进行处理
    }
}
