<!DOCTYPE html>
<html>
	<head>
		<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>云志系统</title>
		<link rel="stylesheet" href="static/css/Login2.css" type="text/css">
		<script src="static/js/jquery-1.11.0.min.js" type="text/javascript"></script>
		<script src="static/js/util.js" type="text/javascript"></script>
		<script src="static/js/formCheck1.js" type="text/javascript"></script>
<%--		<script src="static/js/test.js" type="text/javascript"></script>--%>
	</head>
	<body>
		<!-- <div class="top"></div> -->
		<div class="form">
			<div id="yunrizhi">云志系统</div>
			<form action="userLoginIn" class="content" method="post">
<%--			actionName表示用户行为，通过这个参数可以在UserServlet中判断当前用户想要操作的功能（这个我现在具体还不太懂）	--%>
				<input id="actionName" type="hidden" name="actionName" value="login"/>
				<div id="inputUser">
					<div id="login">用户登录</div>
					<img src="static/img/user.png" alt="" id="userImg">
					<input type="text" id="uname" name="uname" class="inputContent" placeholder="请输入用户名" value="${resultInfo.result.uname}"></br>
					<img src="static/img/pwd.png" alt="" id="pwdImg">
					<input type="password" name="upwd" id="upwd" class="inputContent" placeholder="请输入密码" value="${resultInfo.result.upwd}">
				</div>
				<div id="Remember">
<%--					下面这个要用的话，要在上面的page中添加：isELIgnored="false" 这样后端信息就能回显到前端--%>
					<span id="Message">${resultInfo.msg}</span>
<%--					这里的 value="1" 其实意思就是当checkbox被选中时 value="1"--%>
					<input type="checkbox" name="rem" value="1">
					<span id="RM">记住我</span>
				</div>
				<div id="btn">
					<input type="button" value="登录" id="loginBtn" onclick="checkLogin()">
					<input type="button" value="注册" id="registerBtn" onclick="register()">
				</div>
			</form>
		</div>
    <script>
        <%-- 实现回车登录--%>
        document.onkeydown=function (ev) {
            // 如果按了回车就用checkLogin
            if(ev.key == "Enter") {
                checkLogin();
            }
        }
    </script>
	</body>
</html>
