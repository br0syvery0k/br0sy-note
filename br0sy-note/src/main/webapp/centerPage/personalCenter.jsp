<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<div class="content">
    <div class="pcImgAndWords">
        <img src="static/img/personalCenterPNG.png">
        <span>个人中心</span>
    </div>
    <hr>
    <form action="userLoginIn" method="POST" class="exMsg" enctype="multipart/form-data">
<%--       enctype="multipart/form-data" 为了上传文件 --%>
        <input type="hidden" name="actionName" value="updateUser" id="actionName">
        <span>昵称:</span>
        <input type="text" class="nickName" name="nickName" value="${user.nick}"><br />
        <span>心情:</span>
        <textarea name="mood" id="mood" cols="30" rows="10" placeholder="无">${user.mood}</textarea><br>
        <span>上传头像:</span>
        <input type="file" class="headImg" name="headImg"><br>
    </form>
    <span id="Info1">${resultInfo.msg}</span>
    <button onclick="exMsg()" id="exButton">修改</button>
</div>

<script type="text/javascript">
    // 如果鼠标移开了nickname的输入框（也就是失焦了）
    $(".nickName").blur(function () {
       var nickName = $(".nickName").val();
       if (isEmpty(nickName)) {
           // 提示用户
           $("#Info1").html("昵称不能为空！");
           // 禁用按钮
           $("#exButton").prop("disabled", true);
           return;
       }

        // 判断昵称是否做出了修改
        // 从session作用域中获取用户昵称
        var nick = '${user.nick}';
        // 如果昵称没改
        if (nickName === nick) {
            return;
        }

        // 如果昵称做了修改(利用Ajax进行请求)
        $.ajax({
            type:"get",
            url:"userLoginIn",
            data:{
                actionName:"nickCheck",
                nick:nickName
            },
            success:function (result) {
                if (result == 1) {
                    // 如果可用，清空提示信息，并启动按钮
                    $("#Info1").html("");
                    $("#exButton").prop("disabled", false);
                } else if (result == 0) {
                    $("#Info1").html("昵称不能为空！");
                    $("#exButton").prop("disabled", true);
                } else if (result == 2) {
                    // 如果不可用,提示用户，禁用按钮
                    $("#Info1").html("昵称已存在！");
                    $("#exButton").prop("disabled", true);
                }
            },
            error:function () {
                $("#Info1").html("请求错误。");
            }
        });
    }).focus(function () {
        // 清空提示信息
        $("#Info1").html("");
        // 启用按钮
        $("#exButton").prop("disabled", false);
    });

    if ('${resultInfo.code}' == 1) {
        alert("修改成功！");
    }

    /**
     * 用来对页面进行判断的js文件
     */

    function exMsg() {
        // 前端再校验
        var nickName = $(".nickName").val();
        var moodValue = $("#mood").val();

        // 判断昵称是否做出了修改
        // 从session作用域中获取用户昵称
        var nick = '${user.nick}';
        var mood = '${user.mood}';
        // 如果昵称没改
        if (nickName === nick && moodValue === mood) {
            return;
        }
        // 昵称是否为空
        if (isEmpty(nickName)) {
            $("#Info1").html("昵称不能为空！");
            // return;
        }
        $(".exMsg").submit();
    }

</script>