package com.gec.hrm.servlet;

import com.jdbc.hrm.Bean.Job;
import com.jdbc.hrm.Bean.Page;
import com.jdbc.hrm.Dao.Impl.JobDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet({"/jobselectJob","/joblist.action", "/jobdel.action","/jobedit.action","/joblists.action"})
public class SelectJobServlet extends HttpServlet {
    JobDaoImpl jdi=new JobDaoImpl();//job功能实现类
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();//获取地址的后缀连接字符串
        String action = uri.substring(uri.lastIndexOf("/") + 1);
        int pageIndex=1;//声明分页起始页数
        HttpSession session=request.getSession();//声明session
        if(action.equals("jobselectJob")){//jobselectJob用于初始页面加载时的回应请求
            session.setAttribute("name",null);
        }else if(action.equals("joblist.action")){//joblist.action用于分页变化时的回应请求
            String pageIndex1 = request.getParameter("pageIndex").trim();
            if(!"".equals(pageIndex1)){
                pageIndex=Integer.parseInt(pageIndex1);
            }
        }else if(action.equals("jobdel.action")){//jobdel.action用于删除数据行
            String[] jobIds = request.getParameterValues("jobIds");//获取到被选中的多选框的所有value值，id
            for(String strId:jobIds){//遍历删除
                int i = Integer.parseInt(strId);
                jdi.delJob(i);
            }
        }else if(action.equals("jobedit.action")){//jobedit.action用于处理添加或修改数据行的请求
            int id = Integer.parseInt(request.getParameter("id").trim());//获取到进行修改的jobId
            String name = request.getParameter("name");
            String remark = request.getParameter("remark");
            boolean b = jdi.updateJob(id, name, remark);//调用job功能类的方法添加数据行
            if(b){//返回是否修改成功的信息
                request.setAttribute("massage","修改成功");
            }else {
                request.setAttribute("massage","修改失败");
            }
        }else if(action.equals("joblists.action")){//joblists.action用于用户点击查询时获取到查询表单提交的值，并将session覆盖
            session.setAttribute("name",request.getParameter("name").trim().equals("")?null:request.getParameter("name"));
        }
        //以上连接后台处理完成后都会重新进行返回数据和跳转，跳转位置都为WEB-INF/jsp/job/joblist.jsp
        Page<Job> page = jdi.findPage(pageIndex, (String) session.getAttribute("name"));//获取到分页page对象
        request.setAttribute("joblist",page.getList());
        request.setAttribute("pageModel",page);
        request.getRequestDispatcher("WEB-INF/jsp/job/joblist.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);//所有请求都在doPost进行处理
    }

}
