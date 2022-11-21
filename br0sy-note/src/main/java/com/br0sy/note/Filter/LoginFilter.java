package com.br0sy.note.Filter;

import com.br0sy.note.po.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 非法访问拦截
 *  拦截的资源
 *      所有的资源 /*
 *  需要被放行的资源
 *      1、指定页面（用户无需登录即可访问的资源 登录页面，注册页面）
 *      2、静态资源放行（存放的 js css img 资源）
 *      3、指定行为放行（用户无需登录即可进行的操作，比如 Login）
 *      4、登录状态放行（判断session作用域中是否存在user对象）
 *
 *  自动登录
 *      Session是即时的
 *      Cookie是有一定时间的
 *      什么时候使用免登陆：当用户处于未登录的状态时，调用自动登录功能
 *
 *      目的：
 *          让用户处于登录状态
 *      实现：
 *          从Cookie
 */
@WebFilter("/*")
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 基于HTTP
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 得到访问的路径
        String path = request.getRequestURI();

        // 1、指定页面放行
        if (path.contains("/login.jsp") || path.contains("/register.jsp") || path.contains("/registerSuccess.jsp")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2、静态资源放行
        if (path.contains("/static")) {
            filterChain.doFilter(request, response);
            return;
        }
        // 指定行为放行
        if (path.contains("/userLoginIn")) {
            // 得到用户行为
            String actionName = request.getParameter("actionName");
            // 判断是否是登录状态
            if ("login".equals(actionName) || "register".equals(actionName)) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        // 登录状态放行
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            filterChain.doFilter(request, response);
            return;
        }

        // 自动登录
        // 获取cookies数组
        Cookie[] cookies = request.getCookies();
        // 判断Cookie
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if ("user".equals(cookie.getName())) {
                    String value = cookie.getValue();
                    String[] userInfo = value.split("-");
                    String userName = userInfo[0];
                    String userPwd = userInfo[1];
                    // 请求转发的登录操作
                    String url = "userLoginIn?actionName=login&rem=1&uname="+userName+"&upwd="+userPwd;
                    request.getRequestDispatcher(url).forward(request, response);
                    return;
                }
            }
        }


        // 拦截请求，重定向到登录页面
        response.sendRedirect("login.jsp");
    }

    @Override
    public void destroy() {

    }
}
