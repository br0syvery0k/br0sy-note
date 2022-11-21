// 发送添加的Ajax请求
$('.exchange').click(function () {
    // 获取参数
    var typeName = $('#inputTypes').val();
    // 在数据库中的typeID
    var typeId = $('#inputTypeIds').val();
    // 独立用户的最后一条记录的假ID
    var lastFakeId = $(".ListTable tr:last-child td:nth-of-type(1)").text();
    // 修改按键按下后的假编号
    var fakeID = $("#fakeID").val();

    if (isEmpty(typeName)) {
        // 参数为空
        $('.ex-box-msg').html("类型名称不能为空！");
        return;
    }

    $.ajax({
        type:"get",
        url:"noteType",
        data:{
            actionName:"addTypeOrExType",
            typeName:typeName,
            typeId:typeId,
            fakeId:fakeID
        },
        success:function (data) {
            if(data.resultInfo.code == 0) {
                // 如果添加失败
                // 回显信息
                $(".ex-box-msg").html(data.resultInfo.msg);
            } else if(data.resultInfo.code == 1) {
                // 如果添加成功
                // 隐去模态框
                exit();
                // 操作DOM
                addDOM(lastFakeId, typeName);
                // 提示用户
                alert("类型添加成功！");
            } else if(data.resultInfo.code == 2) {
                // 如果修改成功
                // 隐去模态框
                exit();
                // 操作DOM
                exDOM(fakeID, typeName);
                // 提示用户
                alert("类型修改成功！");
                /**
                 * 现在这里出现了一个bug，当用户修改完类型名称后，在不刷新页面的情况下，再点击修改按钮，此时在input框中的值还是修改前的值
                 *  无法变成修改后的值(实际上在数据库中的值已经改变)，原因是下面的 typeName 没有发生修改，也就是 noteTypes 没有发生修改，
                 *  而noteTypes属于el表达式，如果需要修改，我目前就只知道需要后端用request.setAttribute()方法进行设置(也许还有其他办法)
                 *  但是在ajax请求中，我们无法进行请求转发，所以request.setAttribute()方法设置了也将无效(理论推断，并没有实际做过)
                 *      这里我想了很久，只想到一个办法，就是重构代码，把数据提交用form表单框起来，使用form请求完成，不用ajax，
                 *      但是这样会费我很多时间，这个小bug之后有时间再改。
                 *      回忆起好像很多系统都是这样的...
                 *      简单点的办法暂时还想不到
                 *
                 *      突然想到方法了，我可以再放一个隐藏域，使用隐藏域进行数据传输
                 *          如果隐藏域的typeName有值，那就说明这个类型经过了修改，就将隐藏域中额typeName赋值给原来显示的地方
                 *
                 *      ok 事实证明，上面这个方法是可行的！！！nice这个bug解决了！！
                 */
            }
        }
    });
});

// 添加类型按钮按钮
$(".addList").click(function () {
    // 显示模态框
    $('.shade').css("display","block");
    $('.ex-box').css("display","block");
    // 修改头部信息
    $(".ex-box-header").html("添加类型");
    $(".exchange").html("√添加");
    // 清空ID隐藏域缓存
    $('#inputTypeIds').prop("value", "0");
    $('#fakeID').prop("value", "0");
    // 初始化
    $('#inputTypes').prop("value", "");
    $('.ex-box-msg').html("");
});

// 发送删除表单
$('.yesBtn').click(function () {
    // 获取参数
    var typeId = $("#inputTypeId").val();
    if(isEmpty(typeId)) {
        alert("删除失败，发生了一些未知的错误！");
        return;
    }
    // 表单提交
    $("#deleteTypeForm").submit();
});

// 修改按钮
function borderBox (typeName, typeId, fakeId) {

    /**
     * typeName在这里获取，进行判断
     *      如果input隐藏域中的value没有值，说明没有修改过，那么typeName就用el表达式获取
     *      如果input中的value有值，那就用input中的值
     *      input的value怎么设置值？
     *      可以在exDom中找到input，给value设值！！！明天实现
     */
    var hiddenMsg = $('#hiddenMSG_'+fakeId).val();
    if (!isEmpty(hiddenMsg)) {
        typeName = hiddenMsg;
    }

    // 显示模态框
    $('.shade').css("display","block");
    $('.ex-box').css("display","block");
    // 修改信息
    $(".ex-box-header").html("修改类型");
    $(".exchange").html("√修改");
    $('.ex-box-msg').html("");
    // 模态框动态显示
    $('#inputTypes').prop("value", typeName);
    // 将要修改的类型的typeId放入隐藏域中
    $('#inputTypeIds').prop("value", typeId);
    $('#fakeID').prop("value", fakeId);
}

// 关闭模态框
function exit() {
    $('.shade').css("display","none");
    $('.ex-box').css("display","none");
    $('.de-box').css("display","none");
}

// 添加类型DOM操作
function addDOM(lastFakeId, typeName) {
// 操作前端DOM
    if (isNumber(lastFakeId)) {
        // 不是第一条数据
        var ID = parseInt(lastFakeId) + 1;
        var tr = '<tr><td>'+ID.toString()+'</td><td>'+typeName+'</td><td>';
        tr += '<button onClick="borderBox(\'${noteTypes[i-1].typeName}\', \'${noteTypes[i-1].typeId}\')">修改</button>';
        tr += '&nbsp<button>删除</button></td></tr>';
        var listTyble = $(".ListTable");
        listTyble.append(tr);
    } else {
        // 清空状态栏的话
        $("#emptyNote").html("");
        // 是第一条数据
        var ID = 1;
        var tr = '<tr><td>'+ID.toString()+'</td><td>'+typeName+'</td><td>';
        tr += '<button onClick="borderBox(\'${noteTypes[i-1].typeName}\', \'${noteTypes[i-1].typeId}\')">修改</button>';
        tr += '&nbsp<button onclick="deleteBox(\'${noteTypes[i-1].typeId}\')">删除</button></td></tr>';
        var listTyble = $(".ListTable");
        listTyble.append(tr);
    }
}

//修改类型DOM操作
function exDOM(fakeId, typeName) {
    var ID = parseInt(fakeId) + 1;
    var tdString = '.ListTable tr:nth-of-type('+ID.toString()+') td:nth-of-type(2)';
    // 给input隐藏域赋值
    $('#hiddenMSG_'+fakeId).prop("value", typeName);
    $(tdString).text(typeName);
}

// 判断一个字符串是否是数字
function isNumber(lastFakeId) {
    if(isNaN(Number(lastFakeId))) {
        // 不是数字
        return false;
    } else {
        // 是数字
        return true;
    }
}

// 删除操作模态框
function deleteBox(typeId) {
    // 打开模态框
    $('.shade').css("display","block");
    $('.de-box').css("display","block");
    // 数据读取
    $('#inputTypeId').prop("value", typeId);
}