<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>用户中心</title>
    <link rel="stylesheet" href="static/css/userCenter.css">
    <script src="static/js/jquery-1.11.0.min.js" type="text/javascript"></script>
    <script src="static/js/util.js" type="text/javascript"></script>

<%--    加载富文本编辑器--%>
    <script type="text/javascript" src="static/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" src="static/ueditor/ueditor.all.js"></script>
    <script type="text/javascript" src="static/ueditor/lang/zh-cn/zh-cn.js" charset="utf-8" ></script>
</head>
<body>
<!-- 导航栏 -->
<nav class="navigation">
    <span class="yunzhiSys">云志系统</span>
    <div>
        <img src="static/img/mainPage.png" alt="">
        <a <c:if test="${menu_page=='mainPage'}">class="active"</c:if> href="note?actionName=mainPage" id="mainPage">主页</a>
    </div>
    <div>
        <img src="static/img/postPage.png" alt="">
        <a <c:if test="${menu_page=='postPage'}">class="active"</c:if> href="note?actionName=postPage" id="postPage">发表云记</a>
    </div>
    <div>
        <img src="static/img/ManagePage.png" alt="">
        <a <c:if test="${menu_page=='ManagePage'}">class="active"</c:if> href="noteType?actionName=ManagePage" id="ManagePage">类别管理</a>
    </div>
    <div>
        <img src="static/img/personalCenter.png" alt="">
        <a <c:if test="${menu_page=='personalCenter'}">class="active"</c:if> href="userLoginIn?actionName=personalCenter" id="personalCenter">个人中心</a>
    </div>
    <div>
        <img src="static/img/msgReport.png" alt="">
        <a <c:if test="${menu_page=='msgReport'}">class="active"</c:if> href="userLoginIn?actionName=msgReport" id="msgReport">数据报表</a>
    </div>
</nav>
<!-- 间隔 -->
<header></header>
<div class="container2">
    <div class="static">
        <div class="add">
            <div class="pCenter">
                <img src="static/img/personalCenter.png" alt="" id="personalCenterPng">
                <span>${user.nick}</span>
                <a href="userLoginIn?actionName=Logout" class="quit">退出</a>
                <img src="userLoginIn?actionName=headImg&head=${user.head}" alt="" class="head">
                <span class="mood">${user.mood}</span>
            </div>
            <div class="yunjiTime">

            </div>
            <div class="yunjiClassification">

            </div>
        </div>
        <%--动态包含页面(通过后台设置)--%>
        <c:if test="${empty changePage}">
            <jsp:include page="centerPage/mainPage.jsp"></jsp:include>
        </c:if>
        <c:if test="${!empty changePage}">
            <jsp:include page="${changePage}"></jsp:include>
        </c:if>
    </div>
</div>
<script>
    var isDelete = ${isDelete};
    if (isDelete == "1") {
        alert("删除成功！");
    } else {
        alert("删除失败！");
    }
</script>
</body>
</html>
