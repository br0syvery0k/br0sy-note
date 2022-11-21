package com.br0sy.note.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.br0sy.note.dao.UserDao;
import com.br0sy.note.po.User;
import com.br0sy.note.vo.ResultInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

/**
 * service 业务逻辑层
 * 主要是用来处理业务逻辑的，在dao层的上方
 */
public class UserService {

    private UserDao userDao = new UserDao(); // 数据库层的对象

    /**
     * 作用：返回登录状态
     * @param userName
     * @param userPwd
     * @return
     */
    public ResultInfo<User> userLogin(String userName, String userPwd) {
        ResultInfo<User> resultInfo = new ResultInfo<>();

        // 参数回显，若用户名密码错误，也使其能够保留参数(或者我们只保留账号，不保留密码)
        User u = new User();
        u.setUname(userName);
        u.setUpwd(userPwd);
        resultInfo.setResult(u);

        // 判断参数是否为空
        if (StrUtil.isBlank(userName) || StrUtil.isBlank(userPwd)) {
            resultInfo.setCode(0);
            resultInfo.setMsg("用户名或密码不能为空！");
            // 返回信息
            return resultInfo;
        }

        // 如果不为空，通过用户名查询用户对象
        User user = userDao.queryUserByName(userName);

        // 判断用户是否为空
        if (user == null) {
            // 说明用户是不存在的，那么resultInfo中，就是失败的，返回信息
            resultInfo.setCode(0);
            resultInfo.setMsg("用户不存在！");
            return resultInfo;
        }

        // 判断密码是否正确（md5加密后）
        userPwd = DigestUtil.md5Hex(userPwd);
        if (!userPwd.equals(user.getUpwd())) {
            // 密码不正确
            resultInfo.setCode(0);
            resultInfo.setMsg("用户密码错误！");
            return resultInfo;
        }

        resultInfo.setCode(1);
        resultInfo.setResult(user);

        // 如果对象不为空
        return resultInfo;
    }

    /**
     * 返回注册状态
     * @param userName
     * @param userPwd
     * @return
     */
    public ResultInfo<User> userRegister(String userName, String userPwd, String userNick, String userMood, String userHead) {
        ResultInfo<User> resultInfo = new ResultInfo<>();
        int success = 0;
        User u = new User();
        u.setUname(userName);
//        u.setUpwd(userPwd); //密码是不需要被记住的，所以这里注释了
        u.setNick(userNick);
        u.setMood(userMood);
        u.setHead(userHead);
        resultInfo.setResult(u);

        if(userName != "" && userPwd != "" && userNick != "" && userHead != "0") {
            // 都输入了值 （注意这里的密码是需要 MD5 加密之后才能存入的）

            // 判断是否有账号或昵称冲突
            User haveName0 = userDao.queryUserByName(userName);
            User haveName1 = userDao.queryUserByNick(userNick);
            // 如果账号已经存在，或者昵称已经存在
            if(haveName0 != null) {
                resultInfo.setCode(0);
                resultInfo.setMsg("该用户名已存在！");
                return resultInfo;
            } else if (haveName1 != null) {
                resultInfo.setCode(0);
                resultInfo.setMsg("该昵称已存在！");
                return resultInfo;
            }

            // 密码进行MD5加密
            userPwd = DigestUtil.md5Hex(userPwd);
            // 存入用户并回显信息
            success = userDao.addUser(userName, userPwd, userNick, userMood, userHead);
        }

        if(success > 0) {
            // 查询用户，确保无误，并将用户信息放入 resultInfo 中
            User user = userDao.queryUserByName(userName);
            if (user != null) {
                resultInfo.setCode(1);
                resultInfo.setMsg("注册成功！3秒后跳转登录界面...");
                resultInfo.setResult(user);
            }
        } else {
            // 一般情况下不会发生
            resultInfo.setCode(0);
            resultInfo.setMsg("出现了一些未知的错误");
            return resultInfo;
        }

        return resultInfo;
    }

    /**
     * 检查昵称是否重复
     * 0：未输入
     * 1：昵称没有重复
     * 2：昵称重复
     * 3：没有修改昵称 (这个情况前端不会做任何动作)
     * @param nickName
     * @return
     */
    public Integer userExMsg(String nickName, User user) {
        if (StrUtil.isBlank(nickName)) {
            return 0;
        }
        if (nickName == user.getNick()){
            return 3;
        }
        // 通过传入的nickName判断是否存在这个User，如果存在，返回2；不存在，返回1
        User u = userDao.queryUserByNick(nickName);
        if (u != null) {
           return 2;
        }
        return 1;
    }

    public ResultInfo<User> userUpdateService(HttpServletRequest request) {
        ResultInfo<User> resultInfo = new ResultInfo<>();
        // 获取参数
        String nick = request.getParameter("nickName");
        String mood = request.getParameter("mood");
        // 判空
        if (StrUtil.isBlank(nick)) {
            resultInfo.setCode(0);
            resultInfo.setMsg("用户昵称不能为空！");
            return resultInfo;
        }

        // 获取User
        User user = (User) request.getSession().getAttribute("user");
        user.setNick(nick);
        user.setMood(mood);

        // 文件上传
        try {
            // 获取Part对象
            Part part = request.getPart("headImg");
            // 通过Part对象获取上传文件的文件名
            String header = part.getHeader("Content=Disposition");
            // 获取具体的请求头
            String str = header.substring(header.lastIndexOf("=") + 2);
            // 上传文件名
            String fileName = str.substring(0,str.length() - 1);
            // 判断文件名是否为空
            if (!StrUtil.isBlank(fileName)) {
                user.setHead(fileName);
                // 获取文件的上传路径
                String filePath = request.getServletContext().getRealPath("/WEB-INF/uploadImg");
                // 上传文件
                part.write(filePath + "/" + fileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        int row = userDao.userUpdate(user);
        if (row > 0) {
            resultInfo.setCode(1);
            // 更改Session中的用户信息
            request.getSession().setAttribute("user", user);
        } else {
            resultInfo.setCode(0);
            resultInfo.setMsg("登录失败");
        }
        return resultInfo;
    }

}
