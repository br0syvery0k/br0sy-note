<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="content_managerPage">
    <div class="List1">
        <img src="static/img/manage.png" alt="" class="managePng">
        <span>类型列表</span>
        <button class="addList">添加类型</button>
    </div>
    <hr>
    <table class="ListTable" >
        <tr>
            <td>编号</td>
            <td>类型</td>
            <td>操作</td>
        </tr>
        <c:if test="${!empty noteTypes}">
            <c:forEach var="i" begin="1" end="${noteTypes.size()}">
                <tr>
                    <td>${i}</td>
                    <td>${noteTypes[i-1].typeName}</td>
                    <%-- 存数据的隐藏域--%>
                    <input type="hidden" value="" id="hiddenMSG_${i}">
                    <td>
                        <button onclick="borderBox('${noteTypes[i-1].typeName}', '${noteTypes[i-1].typeId}', '${i}')">修改</button>
                        <button onclick="deleteBox('${noteTypes[i-1].typeId}')">删除</button>
                    </td>
                </tr>
            </c:forEach>
        </c:if>
        <!-- 使用JSTL代码 -->
    </table>
    <c:if test="${empty noteTypes}">
        <div style="font-size: 15px; text-align: center; margin-bottom: 50px" id="emptyNote">未添加任何类型！</div>
    </c:if>
</div>

<!-- 遮罩层 -->
<div class="shade" onclick="exit()"></div>
<!-- 修改模态框 -->
<div class="ex-box">
    <div class="ex-box-header">
        修改类型
    </div>
    <div class="ex-box-body">
        <div>类型名称：</div>
        <input type="text" placeholder="类型名称" id="inputTypes" value="" autocomplete="off">
        <input type="hidden" value="" id="inputTypeIds" autocomplete="off" >
        <input type="hidden" value="" id="fakeID" autocomplete="off" >
    </div>
    <span class="ex-box-msg"></span>
    <div class="ex-box-footer">
        <button class="exit" onclick="exit()">×关闭</button>
        <button class="exchange">√修改</button>
    </div>
</div>
<%-- 删除模态框 --%>
<div class="de-box">
    <form action="noteType" method="post" autocomplete="off" id="deleteTypeForm">
        <input type="hidden" value="" id="inputTypeId" name="typeId">
        <input type="hidden" value="deleteType" id="actionName" name="actionName">
        <div class="de-box-body">确定要删除该类型吗？</div>
    </form>
    <div class="de-box-btn">
        <button class="yesBtn">确定</button>
        <button class="cancelBtn" onclick="exit()">取消</button>
    </div>
</div>
<script src="static/js/util.js" type="text/javascript"></script>
<script src="static/js/ManagePage1.js" type="text/javascript"></script>