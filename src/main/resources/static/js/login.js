$("#loginBtn").click(function () {
    var username = $("#username").val();
    var password = $("#password").val();
    var data = {};
    data.username = username;
    data.password = password;
    var param = JSON.stringify(data);
    var back = $("#back").attr("value");
    var host = window.location.host;
    var protocol = window.location.protocol;
    var url = protocol + "//" + host + back;
    $.ajax({
        url: "/login/ajaxVerify",
        async: false,
        type: "POST",
        contentType : "application/json",
        dataType : "json",
        data: param,
        success: function (resp) {
            if (resp.errorCode === "0"){
                window.location.href=url;
            }
        }
    });
});

$("#logoutBtn").click(function () {
   $.ajax({
       url: "/logout",
       async: false,
       type: "POST",
       success: function (resp) {
           if (resp.errorCode === "0"){
               window.location.reload();
           }
       }
   })
});

$("#registryBtn").click(function () {
    var result = usernameOnBlur();
    if (result !== 0){
        return;
    }
    result = nicknameOnBlur();
    if (result !== 0){
        return;
    }
    result = passwordOnBlur();
    if (result !== 0){
        return;
    }
    result = rePasswordOnBlur();
    if (result !== 0){
        return;
    }
    var username = $("#usernameInRegistry").val();
    var password = $("#passwordInRegistry").val();
    var nickname = $("#nicknameInRegistry").val();
    var rePassword = $("#rePassword").val();
    var data = {};
    data.username = username;
    data.password = password;
    data.nickname = nickname;
    data.rePassword = rePassword;
    var param = JSON.stringify(data);
    $.ajax({
        url: "/registry/ajax",
        async: false,
        type: "POST",
        contentType : "application/json",
        dataType : "json",
        data: param,
        success: function (resp) {
            if (resp.errorCode === "0"){
                layui.use('layer', function(){
                    var layer = layui.layer;
                    layer.open({
                        content: '注册成功，去登录',
                        yes: function(index, layero){
                            var host = window.location.host;
                            var protocol = window.location.protocol;
                            var url = protocol + "//" + host + "/login";
                            window.location.href=url;
                        }
                    });
                });
            }
            else if (resp.errorCode === "5001"){
                rePasswordOnBlur();
            }else if (resp.errorCode === "5002"){
                showTips("该用户名已被注册","#usernameInRegistry");
            } else if (resp.errorCode === "5003"){
                usernameOnBlur();
            } else if (resp.errorCode === "5004" || resp === "5005"){
                passwordOnBlur();
            }
        }
    });
});

function usernameOnBlur(){
    var input = $("#usernameInRegistry").val();
    if (input === ""){
        showTips('请输入用户名', '#usernameInRegistry');
        return 1;
    }
    var regex = /^[0-9a-zA-Z_]{8,16}$/;
    if (!regex.test(input)) {
        showTips('请输入8-16位数字、字母、下划线组成的用户名', '#usernameInRegistry');
        return 2;
    }
    return 0;
}

function nicknameOnBlur(){
    var input = $("#nicknameInRegistry").val();
    if (input === ""){
        showTips('请输入昵称', '#nicknameInRegistry');
        return 1;
    }
    var regex = /\s/
    if (regex.test(input)){
        showTips('昵称不能含有空格','#nicknameInRegistry');
        return 2;
    }
    if (input.length > 10){
        showTips('昵称不能超过十个字符','#nicknameInRegistry');
        return 3;
    }
    return 0;
}

function passwordOnBlur(){
    var lengthReg = /.{8,16}/;
    var input = $("#passwordInRegistry").val();
    if (!lengthReg.test(input)) {
        showTips('请输入8-16位密码','#passwordInRegistry');
        return 1;
    }
    var reg = /^(?![a-zA-z]+$)(?!\d+$)(?![!@#$%^&*]+$)[a-zA-Z\d!@#$%^&*]+$/;
    if (!reg.test(input)){
        showTips('密码至少包含数字、字母、特殊字符中的两种，如：aa1231323A、aaa@32123','#passwordInRegistry');
        return 2;
    }
    return 0;
}

function rePasswordOnBlur(){
    var password = $("#passwordInRegistry").val();
    var rePassword = $("#rePassword").val();
    if (password !== rePassword){
        showTips('两次输入密码不一致','#rePassword');
        return 1;
    }
    return 0;
}


function showTips(text, id) {
    layui.use('layer', function(){
        var layer = layui.layer;
        layer.open({
            type: 4,
            tips: 3,
            time: 2000,
            shade: 0,
            content: [text, id] //数组第二项即吸附元素选择器或者DOM
        });
    });
}