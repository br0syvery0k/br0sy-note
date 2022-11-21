<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>云志系统注册</title>
    <link rel="stylesheet" href="static/css/registerSuccess.css">
    <script src="static/js/jquery-1.11.0.min.js" type="text/javascript"></script>
</head>
<body>
<div class="registerText">
    注册成功！
    <span>三秒后跳转至登录界面...</span>
</div>
<script>
    $(document).ready(function() {
        setTimeout(jump,3000);
        function jump(){
            window.location.href='login.jsp';
        }
    });
</script>
</body>
</html>