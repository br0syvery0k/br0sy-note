package com.br0sy.note.web;

import com.br0sy.note.po.Note;
import com.br0sy.note.po.User;
import com.br0sy.note.service.NoteService;
import com.br0sy.note.service.UserService;
import com.br0sy.note.vo.ResultInfo;
import org.apache.commons.io.FileUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/userLoginIn")
@MultipartConfig // 可以传文件了
public class UserServlet extends HttpServlet {

    private UserService userService = new UserService(); // 用户服务端对象

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException, ServletException {

        // 接收用户行为（这里的request对象就是http发给Servlet的请求）
        String actionName = request.getParameter("actionName");
        // 判断用户行为
        if ("login".equals(actionName)) {
            // 登录
            userLogin(request, response);
        } else if ("register".equals(actionName)) {
            // 注册
            try {
                userRegister(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("Logout".equals(actionName)) {
            // 退出
            userLogout(request, response);
        } else if ("personalCenter".equals(actionName)) {
            // 进入用户中心
            personalCenter(request, response);
        }  else if ("headImg".equals(actionName)) {
            // 加载头像
            headImg(request, response);
        } else if ("nickCheck".equals(actionName)) {
            // 昵称校验
            nickCheck(request, response);
        } else if ("updateUser".equals(actionName)) {
            // 修改用户信息
            updateUser(request, response);
        } else if ("msgReport".equals(actionName)) {
            // 数据报表页面跳转
            msgReport(request, response);
        }
    }

    /**
     *  跳转到数据报表页面，并生成报表
     * @param request
     * @param response
     */
    private void msgReport(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 高亮
        request.setAttribute("menu_page", "msgReport");
        // 设置前台
        request.setAttribute("changePage", "centerPage/msgReport.jsp");

        // 请求转发
        request.getRequestDispatcher("index.jsp").forward(request, response);

    }


    /**
     * 更新用户信息
     * @param request
     * @param response
     */
    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResultInfo<User> resultInfo = userService.userUpdateService(request);
        // 设置属性
        request.setAttribute("resultInfo", resultInfo);
        // 请求转发
        request.getRequestDispatcher("userLoginIn?actionName=personalCenter").forward(request, response);
    }

    /**
     * 校验昵称是否重复
     * @param request
     * @param response
     */
    private void nickCheck(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取ajax传入的nick
        String nickName = request.getParameter("nick");
        // 拿到session作用域中的user
        User user = (User) request.getSession().getAttribute("user");
        // 调动UserService中的方法，进行判断
        Integer code = userService.userExMsg(nickName, user);
        // 发送给Ajax(转换为字符串类型)
        response.getWriter().write(code + "");
        // 关闭资源
        response.getWriter().close();
    }

    /**
     * 加载头像
     * @param request
     * @param response
     */
    private void headImg(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取头像参数名称
        String head = request.getParameter("head");
        // 请求图片(一个Servlet可以通过 getServletContext() 方法得到servlet上下文的引用，从而拓展Web接口)
        // getRealPath 方法是用来得到某个文件在服务端的绝对路径的
        String realPath = request.getServletContext().getRealPath("/WEB-INF/usingImg");
        // 通过图片的完整路径，得到file对象  (这里会不会有文件上传漏洞，因为没有经过过滤)
        File file = new File(realPath + "/" + head);
        // 得到图片后缀
        String pic = head.substring(head.lastIndexOf(".") + 1);
        // 通过不同图片后缀，设置不同的响应类型
        if ("PNG".equalsIgnoreCase(pic)) {
            response.setContentType("image/png");
        } else if ("JPG".equalsIgnoreCase(pic) || "JPEG".equalsIgnoreCase(pic)) {
            response.setContentType("image/jpeg");
        } else if ("GIF".equalsIgnoreCase(pic)) {
            response.setContentType("image/gif");
        }
        // 利用FileUtils的copyFIle()方法，将图片拷贝给浏览器
        FileUtils.copyFile(file, response.getOutputStream());
    }


    /**
     * 用户登录判断，以及网页的重定向。
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void userLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 1、获取参数（姓名、密码）
        String userName = request.getParameter("uname");
        String userPwd = request.getParameter("upwd");

        // 调用UserService层的方法，返回ResultInfo对象
        ResultInfo<User> resultInfo = userService.userLogin(userName, userPwd);

        // 判断登录是否成功
        if (resultInfo.getCode() == 1) {
            // 成功

            // 将user对象存储到Session中
            request.getSession().setAttribute("user", resultInfo.getResult());

            // 判断用户是否记住密码
            String rem = request.getParameter("rem");

            // 如果是则将用户名和密码存入cookie中，设置失效时间，并相应给客户端
            if ("1".equals(rem)) {
                // 得到cookie兑现
                Cookie cookie = new Cookie("user", userName + "-" + userPwd);
                // 设置失效时间
                cookie.setMaxAge(3*24*60*60);
                // 响应给客户端
                response.addCookie(cookie);
            } else {
                // 清空原有的cookie
                Cookie cookie = new Cookie("user", null);
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
            // 重定向到index.jsp界面(也就是跳到主页)
            response.sendRedirect("index");
        } else {
            // 失败

            // 将resultInfo对象设置到request作用域中(也就是绑定一个名称和一个对象)
            request.setAttribute("resultInfo", resultInfo);
            // 请求转发跳转到登录页面并实现页面跳转（保持数据）
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    /**
     * 用户注册页面跳转，将网页重定向为注册页面
     * @param response
     */
    private void userRegister(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
        // 获取参数
        String userName = request.getParameter("userName");
        String userPwd = request.getParameter("userPwd");
        String userNick = request.getParameter("userNick");
        String userMood = request.getParameter("userMood");
        String userHead = request.getParameter("userHead");

        // 调用Service层的方法，返回resultInfo
        ResultInfo<User> resultInfo = userService.userRegister(userName, userPwd, userNick, userMood, userHead);

        // 判断是否注册是否成功
        if (resultInfo.getCode() == 1) {
            // 成功
            // 重定向到注册成功页面
            response.sendRedirect("registerSuccess.jsp");
        } else {
            // 失败
            // 将resultInfo对象设置到request作用域中(也就是绑定一个名称和一个对象)
            request.setAttribute("resultInfo", resultInfo);
            // 请求转发跳转到注册页面
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }

    /**
     * 用户退出，将网页重定向为注册页面
     * @param request
     * @param response
     */
    private void userLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 删除Session
        request.getSession().invalidate();
        // 清除Cookie
        Cookie cookie = new Cookie("user", null);
        cookie.setMaxAge(0);
        // 网页重定向
        response.sendRedirect("login.jsp");
    }

    /**
     * 进入用户中心
     * @param request
     * @param response
     */
    private void personalCenter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置高亮
        request.setAttribute("menu_page", "personalCenter");
        // 设置前台
        request.setAttribute("changePage", "centerPage/personalCenter.jsp");
        // 请求转发
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
