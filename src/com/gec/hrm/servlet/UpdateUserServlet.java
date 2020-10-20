package com.gec.hrm.servlet;

import com.jdbc.hrm.Bean.Page;
import com.jdbc.hrm.Bean.User;
import com.jdbc.hrm.Dao.Impl.UserDaoImpl;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

@WebServlet({"/useradd.action", "/useraddsave.action","/viewUser.action"})
public class UpdateUserServlet extends HttpServlet {
    UserDaoImpl udi=new UserDaoImpl();//user功能实现类
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();//获取地址的后缀连接字符串
        String action = uri.substring(uri.lastIndexOf("/") + 1);
        if(action.equals("useradd.action")){//useradd.action用于跳转到WEB-INF/jsp/user/useradd.jsp添加页面
            request.getRequestDispatcher("WEB-INF/jsp/user/useradd.jsp").forward(request,response);
        }else if(action.equals("useraddsave.action")){//useraddsave.action用于处理添加数据行请求
            String loginname = request.getParameter("loginname");
            User login = udi.findLogin(loginname);
            if(null==login){//判断提交的loginname是否已存在
                String username = request.getParameter("username").trim();
                int status = Integer.parseInt(request.getParameter("status").trim());
                String password = request.getParameter("password").trim();
                Date date=new Date();
                boolean b = udi.addUser(loginname, password, status, date, username);
                if(b){
                    request.setAttribute("message","添加成功");
                }else {
                    request.setAttribute("message","添加失败");
                }
                request.getRequestDispatcher("WEB-INF/jsp/user/useradd.jsp").forward(request,response);
            }else {
                request.setAttribute("message","账号已被占用");
                request.getRequestDispatcher("WEB-INF/jsp/user/useradd.jsp").forward(request,response);
            }
        }else if(action.equals("viewUser.action")){//用于跳转到修改数据行页面
            int id = Integer.parseInt(request.getParameter("id"));//找到要修改的user对象
            User user = udi.findId(id);
            request.setAttribute("user",user);
            request.getRequestDispatcher("WEB-INF/jsp/user/useredit.jsp").forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);//所有请求都在doPost进行处理
    }
}
