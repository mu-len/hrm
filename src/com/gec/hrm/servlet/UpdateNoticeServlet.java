package com.gec.hrm.servlet;

import com.jdbc.hrm.Bean.Notice;
import com.jdbc.hrm.Bean.User;
import com.jdbc.hrm.Dao.Impl.NoticeDaoImpl;
import com.jdbc.hrm.Dao.Impl.TypeDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"/viewNotice.action","/noticeaddNotice","/noticesaveOrUpdate.action"})
public class UpdateNoticeServlet extends HttpServlet {
    NoticeDaoImpl ndi=new NoticeDaoImpl();//notice功能实现类
    TypeDaoImpl tdi=new TypeDaoImpl();//type功能实现类
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();//获取地址的后缀连接字符串
        String action = uri.substring(uri.lastIndexOf("/") + 1);
        if(action.equals("viewNotice.action")){//用于跳转到修改数据行页面
            int id = Integer.parseInt(request.getParameter("id"));//找到要修改的notice对象
            request.setAttribute("notice",ndi.findOne(id));
            request.setAttribute("types",tdi.findAll());
            request.getRequestDispatcher("WEB-INF/jsp/notice/notice_save_update.jsp").forward(request,response);
        }else if(action.equals("noticeaddNotice")){//用于跳转到添加数据页面
            request.setAttribute("types",tdi.findAll());
            request.getRequestDispatcher("WEB-INF/jsp/notice/notice_save_add.jsp").forward(request,response);
        }else if(action.equals("noticesaveOrUpdate.action")){//noticesaveOrUpdate.action用于处理添加或修改数据行的请求
            String id = request.getParameter("id");
            String text = request.getParameter("text").trim();
            String name = request.getParameter("name").trim();
            int type_id = Integer.parseInt(request.getParameter("type_id"));
            if("".equals(id)){//true为添加公告，false为修改公告
                User user = (User)request.getSession().getAttribute("user_session");//获取到正在添加数据行的user对象
                boolean b = ndi.addNotice(name, type_id, text, user.getId());//调用notice功能类的方法添加数据行
                if(b){
                    request.setAttribute("message","添加成功");
                }else {
                    request.setAttribute("message","添加失败");
                }
                request.setAttribute("types",tdi.findAll());
                request.getRequestDispatcher("WEB-INF/jsp/notice/notice_save_add.jsp").forward(request,response);
            }else {
                int i = Integer.parseInt(id);
                boolean b = ndi.updateNotice(i, name, type_id, text);//调用notice功能类的方法修改数据行
                if(b){
                    request.setAttribute("message","修改成功");
                }else {
                    request.setAttribute("message","修改失败");
                }
                request.getRequestDispatcher("WEB-INF/jsp/notice/notice_save_update.jsp").forward(request,response);
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);//所有请求都在doPost进行处理
    }
}
