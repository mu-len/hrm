package com.gec.hrm.servlet;

import com.jdbc.hrm.Bean.Dept;
import com.jdbc.hrm.Bean.Page;
import com.jdbc.hrm.Dao.Impl.DeptDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet({"/deptselectDept", "/deptlist.action", "/deptaddDept","/saveOrUpdate.action","/viewDept.action","/deptdel.action","/deptlists.action"})
public class SelectDeptServlet extends HttpServlet {
    DeptDaoImpl ddi=new DeptDaoImpl();//dept功能实现类
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();//获取地址的后缀连接字符串
        String action = uri.substring(uri.lastIndexOf("/") + 1);
        int pageIndexs=1;//声明分页起始页数
        HttpSession session=request.getSession();//声明session
        if(action.equals("deptselectDept")){//deptselectDept用于初始页面加载时的回应请求
            session.setAttribute("name",null);//如果是第一次加载设置对应的session，值都为null
        }else if(action.equals("deptlist.action")){//deptlist.action用于分页变化时的回应请求
            String pageIndex = request.getParameter("pageIndex").trim();
            if(!pageIndex.equals("")){//否则页面不是第一次加载，表单已经初始化，判断表单提交的pageIndex是否为""，不是""就将分页起始数pageIndex的值设置为提交过来的值
                pageIndexs=Integer.parseInt(pageIndex);
            }
        }else if(action.equals("deptaddDept")){//跳转到添加数据行页面
            request.getRequestDispatcher("WEB-INF/jsp/dept/deptedit.jsp").forward(request,response);
            return;
        }else if(action.equals("saveOrUpdate.action")){//处理添加或修改数据行请求
            String id = request.getParameter("id").trim();
            if("".equals(id)){//true为添加数据行，false为修改数据行
                String name = request.getParameter("name");
                Dept dept = ddi.findDept(name);
                if(null!=dept){//判断部门是否已经存在
                    request.setAttribute("massage","已存在该部门");
                }else {
                    String remark = request.getParameter("remark").trim();
                    boolean b = ddi.addDept(name, remark, "0");//调用dept实现类方法添加数据
                    if(b){//返回是否添加成功信息
                        request.setAttribute("massage","添加成功");
                    }else {
                        request.setAttribute("massage","添加失败");
                    }
                }
                request.getRequestDispatcher("WEB-INF/jsp/dept/deptedit.jsp").forward(request,response);
                return;
            }else {
                int ids=Integer.parseInt(id);
                String name = request.getParameter("name").trim();
                String remark = request.getParameter("remark").trim();
                boolean b = ddi.updateDept(ids, name, remark);//调用dept实现类方法修改数据
                if(b){//返回是否修改成功信息
                    request.setAttribute("massage","修改成功");
                }else {
                    request.setAttribute("massage","修改失败");
                }
            }
        }else if(action.equals("viewDept.action")){//跳转到修改数据行页面
            String id = request.getParameter("id").trim();
            Dept dept = ddi.findDept(Integer.parseInt(id));//找到要进行修改的dept对象
            request.setAttribute("dept",dept);
            request.getRequestDispatcher("WEB-INF/jsp/dept/deptedit.jsp").forward(request,response);
            return;
        }else if(action.equals("deptdel.action")){//deptdel.action用于删除数据行
            String[] deptIds = request.getParameterValues("deptIds");
            if(deptIds.length>0){
                for(String strId:deptIds){//遍历删除
                    int i = Integer.parseInt(strId);
                    ddi.delDate(i);
                }
            }
        }else if(action.equals("deptlists.action")){//deptlists.action用于用户点击查询时获取到查询表单提交的值，并将session覆盖
            session.setAttribute("name",request.getParameter("name").trim().equals("")?null:request.getParameter("name"));
        }
        //以上连接后台处理完成后都会重新进行返回数据和跳转，跳转位置都为WEB-INF/jsp/dept/deptlist.jsp
        Page<Dept> page= ddi.findPage(pageIndexs, (String) session.getAttribute("name"));//获取到分页page对象
        request.setAttribute("deptlist",page.getList());
        request.setAttribute("pageModel",page);
        request.getRequestDispatcher("WEB-INF/jsp/dept/deptlist.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);//所有请求都在doPost进行处理
    }
}
