<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>云志系统注册</title>
    <link rel="stylesheet" href="./static/css/register1.css">
    <script src="static/js/jquery-1.11.0.min.js" type="text/javascript"></script>
    <script src="static/js/util.js" type="text/javascript"></script>
    <script src="static/js/formCheck1.js" type="text/javascript"></script>
</head>
<body>
<!--
用户名
密码
昵称
头像（下拉菜单，默认限定的头像）
心情
-->
<div class="container">
    <div class="registerText">
        <form action="userLoginIn" method="POST" class="registerForm" autocomplete="off" >
            <input type="hidden" value="register" id="actionName" name="actionName">
            <input type="text" id="userName" placeholder="用户名*" name="userName">
            <input type="password" id="userPwd" placeholder="密码*" name="userPwd">
            <input type="text" id="userNick" placeholder="昵称*" name="userNick">
            <input type="text" id="userMood" name="userMood" placeholder="心情">
            <select name="userHead" id="userHead">
                <!-- 这里可以考虑用js做一个头像切换 -->
                <option value="0">请选择你的头像*</option>
                <option value="404.jpg">404.jpg</option>
                <option value="555.jpg">555.jpg</option>
                <option value="666.jpg">666.jpg</option>
            </select>
            <span id="registerMsg">${resultInfo.msg}</span>
            <input type="button" value="注册" id="registerButton" onclick="checkRegister()">
        </form>
    </div>
    <div class="img">
        <img src="./static/img/file(1).jpeg" alt="" id="img1">
    </div>
</div>
</body>
</html>