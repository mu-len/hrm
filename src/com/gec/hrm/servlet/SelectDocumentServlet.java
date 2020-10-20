package com.gec.hrm.servlet;

import com.jdbc.hrm.Bean.Document;
import com.jdbc.hrm.Bean.Page;
import com.jdbc.hrm.Dao.Impl.DocumentDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet({"/documentlist.action","/documentlists.action","/removeDocument"})
public class SelectDocumentServlet extends HttpServlet {
    DocumentDaoImpl dmdi=new DocumentDaoImpl();//document功能实现类
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();//获取地址的后缀连接字符串
        String action = uri.substring(uri.lastIndexOf("/") + 1);
        HttpSession session=request.getSession();//声明session
        int pageIndex=1;//声明分页起始页数
        if(action.equals("documentlist.action")){//documentlist.action用于初始页面加载或者分页变化时的回应请求
            String title = request.getParameter("title");
            if(null==title){//第一次请求页面未加载时获取的值为null，除此之外为""或者其他值
                session.setAttribute("title",null);//如果是第一次加载设置对应的session，值都为null
            }else {//否则页面不是第一次加载，表单已经初始化，判断表单提交的pageIndex是否为""，不是""就将分页起始数pageIndex的值设置为提交过来的值
                String pageIndex1 = request.getParameter("pageIndex").trim();
                if(!pageIndex1.equals("")){
                    pageIndex=Integer.parseInt(pageIndex1);
                }
            }
        }else if(action.equals("documentlists.action")){//documentlists.action用于用户点击查询时获取到查询表单提交的值，并将session覆盖
            session.setAttribute("title",request.getParameter("title").trim().equals("")?null:request.getParameter("title"));
        }else if(action.equals("removeDocument")){//removeDocument用于删除数据行
            String ids = request.getParameter("ids").trim();//获取到被选中的多选框的所有value值，id
            for(String strId:ids.split(",")){//遍历删除
                int i = Integer.parseInt(strId);
                dmdi.delDocument(i);
            }
        }
        //以上连接后台处理完成后都会重新进行返回数据和跳转，跳转位置都为WEB-INF/jsp/document/documentlist.jsp
        Page<Document> page=dmdi.findPage(pageIndex,(String) session.getAttribute("title"));//获取到分页page对象
        request.setAttribute("documentlist",page.getList());
        request.setAttribute("pageModel",page);
        request.getRequestDispatcher("WEB-INF/jsp/document/documentlist.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);//所有请求都在doPost进行处理
    }
}
