package com.br0sy.note.Filter;

import cn.hutool.core.util.StrUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 字符中文乱码处理
 */
@WebFilter("/*") // 过滤所有资源
public class FilterServlet implements Filter {

    // 重写接口
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    // 重写接口
    @Override
    public void destroy() {

    }

    // 防止中文乱码
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 基于HTTP
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 处理POST请求
        request.setCharacterEncoding("UTF-8");

        // 得到请求类型
        String Method = request.getMethod();

        // GET请求
        if ("GET".equalsIgnoreCase(Method)) {
            // 得到服务器版本
            String serverInfo = request.getServletContext().getServerInfo();
            // 通过截取字符串，判断版本号
            String version = serverInfo.substring(serverInfo.lastIndexOf("/")+1,serverInfo.indexOf("."));
            // 判断服务器版本
            if (version != null && Integer.parseInt(version) < 8) {
                // Tomcat7及以下版本的服务器get请求
                MyWapper myWapper = new MyWapper(request);
                // 放行资源
                filterChain.doFilter(myWapper,response);
                return;
            }
        }
        // 放行资源
        filterChain.doFilter(request,response);
    }

    /**
     * 定义内部类（类的本质是request对象）
     */
    class MyWapper extends HttpServletRequestWrapper {

        private HttpServletRequest request;

        /**
         * 带参构造
         *  可以得到需要处理的 request 对象
         * @param request
         */
        public MyWapper(HttpServletRequest request) {
            super(request);
            this.request = request;
        }

        /**
         * 重写getParameter
         * @param name
         * @return
         */
        @Override
        public String getParameter(String name) {
            // 获取参数
            String value = request.getParameter(name);
            // 判断参数是否为空
            if (StrUtil.isBlank(value)) {
                return value;
            }

            try {
                value = new String(value.getBytes("ISO-8859-1"),"UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return value;
        }
    }
}
