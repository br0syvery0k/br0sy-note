/**
 * 用户登录的表单校验
 * 1、获取用户名和密码
 * 2、判断用户名和密码是否为空
 * 3、如果不为空提交表单，如果为空，提示用户
 */
function checkLogin() {
    // 1、获取用户名和密码(用jquery)
    var userName = $("#uname").val();
    var userPwd = $("#upwd").val();
    //判断获取的用户名或密码是否为空
    if(isEmpty(userName)) {
        $("#Message").html("输入的用户名不能为空！");
        return;
    }
    if(isEmpty(userPwd)) {
        $("#Message").html("输入的密码不能为空！");
        return;
    }
    // 如果上面都通过了，那么就提交表单
    // 这里解释一下，因为我们是在button上面写代码，所以需要手动提交表单，如果type是submit的话，我们就不需要这样了，但是submit就执行
    // 不了上面的代码
    $(".content").submit();
}

// 注册界面的跳转
function register() {
    // 这里由于仅仅只是注册页面跳转，不需要其他操作，所以直接用js跳转
    window.location.href="register.jsp";
}

// 检查注册表单
function checkRegister() {
    // 获取数据
    var userName = $("#userName").val();
    var userPwd = $("#userPwd").val();
    var userNick = $("#userNick").val();
    var userHead = $("#userHead").val();

    // 判断不能为空
    if(isEmpty(userName)) {
        $("#registerMsg").html("输入的用户名不能为空！");
        return;
    }
    if(isEmpty(userPwd)) {
        $("#registerMsg").html("输入的密码不能为空！");
        return;
    }
    if(isEmpty(userNick)) {
        $("#registerMsg").html("输入的昵称不能为空");
        return;
    }
    if(userHead == "0") {
        $("#registerMsg").html("请选择你的头像！");
        return;
    }

    // 提交表单
    $(".registerForm").submit();
}