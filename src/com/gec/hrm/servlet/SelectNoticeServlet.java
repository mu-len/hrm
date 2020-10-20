package com.gec.hrm.servlet;

import com.jdbc.hrm.Bean.Notice;
import com.jdbc.hrm.Bean.Page;
import com.jdbc.hrm.Dao.Impl.NoticeDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet({"/noticelist.action","/querynotice.action","/noticedel.action","/noticelists.action"})
public class SelectNoticeServlet extends HttpServlet {
    NoticeDaoImpl ndi=new NoticeDaoImpl();//notice功能实现类
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();//获取地址的后缀连接字符串
        String action = uri.substring(uri.lastIndexOf("/") + 1);
        int pageIndex=1;//声明分页起始页数
        HttpSession session=request.getSession();//声明session
        if(action.equals("noticelist.action")){//noticelist.action用于初始页面加载或者分页变化时的回应请求
            if(null==request.getParameter("name")){//第一次请求页面未加载时获取的值为null，除此之外为""或者其他值
                session.setAttribute("name",null);//如果是第一次加载设置对应的session，值都为null
            }else {//否则页面不是第一次加载，表单已经初始化，判断表单提交的pageIndex是否为""，不是""就将分页起始数pageIndex的值设置为提交过来的值
                String pageIndex1 = request.getParameter("pageIndex").trim();
                if(!"".equals(pageIndex1)){
                    pageIndex=Integer.parseInt(pageIndex1);
                }
            }
        }else if(action.equals("noticedel.action")){//noticedel.action用于删除数据行
            String[] noticeIds = request.getParameterValues("noticeIds");//获取到被选中的多选框的所有value值，id
            if(noticeIds.length>0){
                for(String strId:noticeIds){//遍历删除
                    int id = Integer.parseInt(strId);
                    ndi.delNotice(id);
                }
            }
        }else if(action.equals("noticelists.action")){//noticelists.action用于用户点击查询时获取到查询表单提交的值，并将session覆盖
            session.setAttribute("name",request.getParameter("name").trim().equals("")?null:request.getParameter("name"));
        }
        //以上连接后台处理完成后都会重新进行返回数据和跳转，跳转位置都为WEB-INF/jsp/notice/noticelist.jsp
        Page<Notice> page = ndi.findPage(pageIndex, (String) session.getAttribute("name"));//获取到分页page对象
        request.setAttribute("noticelist",page.getList());
        request.setAttribute("pageModel",page);
        request.getRequestDispatcher("WEB-INF/jsp/notice/noticelist.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);//所有请求都在doPost进行处理
    }
}
