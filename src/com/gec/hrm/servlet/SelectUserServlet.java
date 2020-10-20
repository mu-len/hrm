package com.gec.hrm.servlet;

import com.jdbc.hrm.Bean.Page;
import com.jdbc.hrm.Bean.User;
import com.jdbc.hrm.Dao.Impl.UserDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet({"/userlist.action", "/userdel.action","/userlistpage.action","/useredit.action","/userlists.action"})
public class SelectUserServlet extends HttpServlet {
    UserDaoImpl udi=new UserDaoImpl();//user功能实现类
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        String action = uri.substring(uri.lastIndexOf("/") + 1);//获取地址的后缀连接字符串
        int pageIndex=1;//声明分页起始页数
        HttpSession session=request.getSession();//声明session
        if(action.equals("userlist.action")){//userlist.action用于初始页面加载或者分页变化时的回应请求
            String loginname = request.getParameter("loginname");//第一次请求页面未加载时获取的值为null，除此之外为""或者其他值
            String username = request.getParameter("username");
            String status = request.getParameter("status");
            if(null==loginname&&null==username&&null==status){//如果是第一次加载设置三个对应的session，值都为null
                session.setAttribute("loginname",null);
                session.setAttribute("username",null);
                session.setAttribute("status",null);
            }else {//否则页面不是第一次加载，表单已经初始化，判断表单提交的pageIndex是否为""，不是""就将分页起始数pageIndex的值设置为提交过来的值
                if(!request.getParameter("pageIndex").equals("")){
                    pageIndex=Integer.parseInt(request.getParameter("pageIndex"));
                }
            }
        }else if(action.equals("userdel.action")){//userdel.action用于删除数据行
            String[] userIds = request.getParameterValues("userIds");//获取到被选中的多选框的所有value值，id
            for(String strId:userIds){//遍历删除
                if(!strId.trim().equals("")){
                    int i = Integer.parseInt(strId);
                    udi.delId(i);
                }
            }
        }else if(action.equals("useredit.action")){//useredit.action用于修该数据行
            int id = Integer.parseInt(request.getParameter("id").trim());//获取各表单项的值，之后提交修改
            String username = request.getParameter("username").trim();
            String loginname = request.getParameter("loginname").trim();
            int status = Integer.parseInt(request.getParameter("status").trim());
            String password = request.getParameter("password").trim();
            udi.updateUser(id, loginname, password, status, username);
        }else if(action.equals("userlists.action")){//userlists.action用于用户点击查询时获取到查询表单提交的值，并将session覆盖
            session.setAttribute("loginname",request.getParameter("loginname").trim().equals("")?null:request.getParameter("loginname"));
            session.setAttribute("username",request.getParameter("username").trim().equals("")?null:request.getParameter("username"));
            session.setAttribute("status",request.getParameter("status").trim().equals("")?null:request.getParameter("status"));
        }
        //以上连接后台处理完成后都会重新进行返回数据和跳转，跳转位置都为WEB-INF/jsp/user/userlist.jsp
        Page<User> page = udi.findPage(pageIndex, (String) session.getAttribute("loginname"), (String) session.getAttribute("username"),(String) session.getAttribute("status"));//获取到分页page对象
        request.setAttribute("userlist",page.getList());
        request.setAttribute("pageModel",page);
        request.getRequestDispatcher("WEB-INF/jsp/user/userlist.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);//所有请求都在doPost进行处理
    }
}
