package cn.tycoding.util;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.tycoding.pojo.Admin;
/**
 *  查看用户是否登录过，未登录禁止访问页面(重定向到登陆页面)
 *	作者		:	pyf<br>
 */
public class AuthFilter implements Filter {
	public void destroy() {
	}
 
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)  throws IOException, ServletException {        
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        HttpSession session = httpServletRequest.getSession();
        if(session == null){
        System.out.println(session);
        }
        String us_name = (String) session.getAttribute("us_name");
        System.out.println(us_name);

        String url = httpServletRequest.getRequestURI();

        //若不进行url.endsWith("login.html")判断则会出现无限循环重定向的问题；
        //若登陆成功之后则us_id不为null，继续执行
        if(url.endsWith("login.html") || us_name!=null){        
               chain.doFilter(httpServletRequest, httpServletResponse);
               return;
        }

        //若该if放在上一if语句之前，仍然会出现无限循环重定向的问题
        if(us_name==null){
               httpServletResponse.sendRedirect("/login.html");
               return;
        }
  }
 
	public void init(FilterConfig filterConfig) throws ServletException {
	}
}
