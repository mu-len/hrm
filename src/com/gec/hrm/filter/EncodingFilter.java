package com.gec.hrm.filter;

import java.io.IOException;
import java.net.http.HttpResponse;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class EncodingFilter implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub
	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request1=(HttpServletRequest)request;
		HttpServletResponse response1=(HttpServletResponse)response;
		request1.setCharacterEncoding("utf-8");
		response1.setCharacterEncoding("utf-8");
		response1.setContentType("text/html;charset=utf-8");
		String URI = request1.getRequestURI();
		//    排除静态页面
		if (URI.contains(".css") || URI.contains(".js") || URI.contains(".png")) {
			chain.doFilter(request, response);
			return ;
		}
		String uri = request1.getRequestURI();//获取地址的后缀连接字符串
		String action = uri.substring(uri.lastIndexOf("/") + 1);
		if(null!=request1.getSession().getAttribute("user_session")){
			chain.doFilter(request1, response1);
		}else {
			if (action.equals("login.action") && null != request1.getParameter("loginname") && null != request1.getParameter("password")) {
				chain.doFilter(request1, response1);
			} else {
				request1.getRequestDispatcher("loginForm.action").forward(request1, response1);

			}
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
