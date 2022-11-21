<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="content_mainPage">
    <div class="mainPage_header">
        <img src="static/img/eye.png" alt="">
        <span>查看云记</span>
        <button class="checkNote_exbtn"><a href="note?actionName=mainPage" style="font-size: 15px; text-decoration: none; color: black;">修改</a></button>
        <button class="checkNote_delbtn"><a href="note?actionName=deleteNote&noteId=${note.noteId}" style="font-size: 15px; text-decoration: none; color: black;">删除</a></button>
        <button class="checkNote_backbtn"><a href="note?actionName=mainPage" style="font-size: 15px; text-decoration: none; color: black;">返回</a></button>
    </div>
    <hr>
    <div class="mainPage_body">
        <div class="checkNote_title">${note.title}</div>
        <div class="checkNote_content">${note.content}</div>
    </div>
</div>
<script>

</script>
