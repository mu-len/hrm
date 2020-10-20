package com.gec.hrm.servlet;

import com.jdbc.hrm.Bean.Page;
import com.jdbc.hrm.Bean.Type;
import com.jdbc.hrm.Bean.User;
import com.jdbc.hrm.Dao.Impl.TypeDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet({"/typelist.action","/typedel.action","/typesaveOrUpdate.action","/typelists.action"})
public class SelectTypeServlet extends HttpServlet {
    TypeDaoImpl tdi=new TypeDaoImpl();//type功能实现类
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();//获取地址的后缀连接字符串
        String action = uri.substring(uri.lastIndexOf("/") + 1);
        HttpSession session=request.getSession();//声明session
        int pageIndex=1;//声明分页起始页数
        if(action.equals("typelist.action")){//typelist.action用于初始页面加载或者分页变化时的回应请求
            if(null==request.getParameter("name")){//第一次请求页面未加载时获取的值为null，除此之外为""或者其他值
                session.setAttribute("name",null);//如果是第一次加载设置对应的session，值都为null
            }else {//否则页面不是第一次加载，表单已经初始化，判断表单提交的pageIndex是否为""，不是""就将分页起始数pageIndex的值设置为提交过来的值
                String pageIndex1 = request.getParameter("pageIndex");
                if(!"".equals(pageIndex1)){
                    pageIndex = Integer.parseInt(pageIndex1);
                }
            }
        }else if(action.equals("typedel.action")){//typedel.action用于删除数据行
            String[] typeIds = request.getParameterValues("typeIds");//获取到被选中的多选框的所有value值，id
            if(typeIds.length>0){
                for(String strId:typeIds){//遍历删除
                    int i = Integer.parseInt(strId);
                    tdi.delType(i);
                }
            }
        }else if(action.equals("typesaveOrUpdate.action")){//typesaveOrUpdate.action用于处理添加或修改数据行的请求
            String id1 = request.getParameter("id").trim();//获取提交的typeId
            String name = request.getParameter("name").trim();//获取到提交的typename
            if("".equals(id1)){//id值为""时为添加数据行
                User user=(User)session.getAttribute("user_session");
                boolean b = tdi.addType(name, user.getId());//调用type功能类的方法添加数据行
                if(b){//返回是否添加成功的信息
                    request.setAttribute("message","添加成功");
                }else {
                    request.setAttribute("message","添加失败");
                }
                request.getRequestDispatcher("WEB-INF/jsp/notice/type_save_update.jsp").forward(request,response);
                return;
            }else {//id值不是""是为修改数据行
                int id = Integer.parseInt(id1);
                boolean b = tdi.updateType(id, name);//调用type功能类的方法修改数据行
                if(b){
                    request.setAttribute("message","修改成功");
                }else {
                    request.setAttribute("message","修改失败");
                }
            }
        }else if(action.equals("typelists.action")){//typelists.action用于用户点击查询时获取到查询表单提交的值，并将session覆盖
            session.setAttribute("name",request.getParameter("name").trim().equals("")?null:request.getParameter("name"));
        }
        //以上连接后台处理完成后都会重新进行返回数据和跳转，跳转位置都为WEB-INF/jsp/notice/typelist.jsp
        Page<Type> page = tdi.findPage(pageIndex, (String) session.getAttribute("name"));//获取到分页page对象
        request.setAttribute("typelist",page.getList());
        request.setAttribute("pageModel",page);
        request.getRequestDispatcher("WEB-INF/jsp/notice/typelist.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);//所有请求都在doPost进行处理
    }
}
