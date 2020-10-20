package com.gec.hrm.servlet;

import com.jdbc.hrm.Dao.Impl.TypeDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"/viewType.action","/noticeaddType"})
public class UpdateTypeServlet extends HttpServlet {
    TypeDaoImpl tdi=new TypeDaoImpl();//type功能实现类
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();//获取地址的后缀连接字符串
        String action = uri.substring(uri.lastIndexOf("/") + 1);
        if(action.equals("viewType.action")){//跳转到修改数据行页面
            int id = Integer.parseInt(request.getParameter("id"));//找到需要修改的type对象
            request.setAttribute("type",tdi.findOne(id));
            request.getRequestDispatcher("WEB-INF/jsp/notice/type_save_update.jsp").forward(request,response);
        }else if(action.equals("noticeaddType")){//跳转到添加数据行页面
            request.getRequestDispatcher("WEB-INF/jsp/notice/type_save_update.jsp").forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);//所有请求都在doPost进行处理
    }
}
