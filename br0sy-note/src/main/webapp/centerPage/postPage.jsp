<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="content_postPage">
    <div class="postPage_header">
        <img src="static/img/cloudDownload.png" alt="">
        <span>发布云记</span>
    </div>
    <hr>
    <c:if test="${!empty noteTypes}">
        <form action="note" method="POST" autocomplete="off" id="postPage_Form">
            <input type="hidden" name="actionName" id="actionName" value="addPageContent">
            <div class="postPage_form_1">
                <span>类别：</span>
                <select name="LoadNoteType" id="LoadNoteType">
                    <option value="">请选择云记类别</option>
                    <c:forEach var="item" items="${noteTypes}">
                        <option value="${item.typeId}" <c:if test="${noteResultInfo.result.typeId == item.typeId}">selected</c:if>>${item.typeName}</option>
                    </c:forEach>
                    <!-- 使用JSTL -->
                </select>
            </div>
            <div class="postPage_form_2">
                <span>标题：</span>
                <input type="text" name="noteName" id="noteName" value="${noteResultInfo.result.title}">
            </div>
            <div class="postPage_form_3">
                <textarea id="content" name="content">${noteResultInfo.result.content}</textarea>
            </div>
        </form>
        <button class="okBtn">保存</button>
        <span class="postPage_hiddenMsg" style="font-size: 15px; color: red;">${noteResultInfo.msg}</span>
    </c:if>
    <c:if test="${empty noteTypes}">
        <div style="font-size: 30px">你还没有添加任何云记类型哦！</div>
        <a href="noteType?actionName=ManagePage">点此处添加类型</a>
    </c:if>
</div>

<script type="text/javascript">
    var ue;
    $(function () {
        // 富文本编辑器加载
        ue = UE.getEditor('content');
        if ('${noteResultInfo.code}' == 1) {
            $(".postPage_hiddenMsg").html("保存成功！");
        }
    });
    $(".okBtn").click(function () {
        var noteType = $("#LoadNoteType").val();
        var title = $("#noteName").val();
        var content = ue.getContent();

        if (isEmpty(noteType)) {
            $(".postPage_hiddenMsg").html("请选择类别！");
            return;
        }

        if (isEmpty(title)) {
            $(".postPage_hiddenMsg").html("标题不能为空！");
            return;
        }

        if (isEmpty(content)) {
            $(".postPage_hiddenMsg").html("内容不能为空！");
            return;
        }

        $("#postPage_Form").submit();
    });
</script>