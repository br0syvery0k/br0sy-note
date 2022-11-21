<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="content_mainPage">
    <div class="mainPage_header">
        <img src="static/img/mainPage2.png" alt="">
        <span>云记列表</span>
    </div>
    <hr>
    <div class="mainPage_body">
        <c:if test="${empty notes}" >
            <div style="font-size: 30px">你还没有发表过任何云记哦！</div>
            <a href="note?actionName=postPage">点此处发表云记</a>
        </c:if>
        <c:if test="${!empty notes}" >
            <table class="yunjiList">
                <tr class="yunjiList_title">
                    <td>编号</td>
                    <td>标题</td>
                    <td>类型</td>
                    <td>时间</td>
                </tr>
                <c:forEach var="i" begin="${(myPageUtil.nowPageNum - 1) * myPageUtil.maxArticleInOnePage + 1}" end="${myPageUtil.lastArticleNumber}">
                    <tr>
                        <td>${i}</td>
                        <td><a style="text-decoration: none;" href="note?actionName=inquireNote&noteId=${notes[i-(myPageUtil.nowPageNum - 1) * myPageUtil.maxArticleInOnePage - 1].noteId}">${notes[i-(myPageUtil.nowPageNum - 1) * myPageUtil.maxArticleInOnePage - 1].title}</a></td>
                        <td>${notes[i-(myPageUtil.nowPageNum - 1) * myPageUtil.maxArticleInOnePage - 1].typeName}</td>
                        <td>${notes[i-(myPageUtil.nowPageNum - 1) * myPageUtil.maxArticleInOnePage - 1].noteTime}</td>
                    </tr>
                </c:forEach>
            </table>
            <div class="pageNum">
                <c:if test="${myPageUtil.prePageNum > 0}">
                    <a href="note?actionName=mainPage&nowPageNum=${myPageUtil.nowPageNum - 1}" class="prev">&lt;</a>
                </c:if>
                <c:forEach var="i" begin="1" end="${myPageUtil.pageCount}">
                    <a href="note?actionName=mainPage&nowPageNum=${i}" <c:if test="${myPageUtil.nowPageNum == i}"> class="current" </c:if> >${i}</a>
                </c:forEach>
                <c:if test="${myPageUtil.nextPageNum > 0}">
                    <a href="note?actionName=mainPage&nowPageNum=${myPageUtil.nowPageNum + 1}" class="next">&gt;</a>
                </c:if>
            </div>
        </c:if>
    </div>
    <span class="postPage_hiddenMsg"></span>
</div>