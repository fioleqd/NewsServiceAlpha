<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>注册</title>
    <meta name="description" content="">
    <meta name="keywords" content="">
    <link rel="stylesheet" media="screen" href="/css/loginstyle.css">
    <link rel="stylesheet" type="text/css" href="/css/reset.css"/>
    <link rel="stylesheet" type="text/css" href="/layui/css/layui.css">
    <link rel="shortcut icon" href="/image/shorticon.png">
    <script src="/js/jquery-2.1.4.min.js"></script>
</head>
<body>
<div id="particles-js">
    <div class="login">
        <div class="registry-top">
            注册
        </div>
        <div class="login-center clearfix">
            <div class="login-center-img"><img src="/image/name.png"/></div>
            <div class="login-center-input">
                <input type="text" id="usernameInRegistry" placeholder="请输入8-16位用户名" onblur="usernameOnBlur()"/>
                <div class="login-center-input-text">用户名</div>
            </div>
        </div>
        <div class="login-center clearfix">
            <div class="login-center-img"><img src="/image/name.png"/></div>
            <div class="login-center-input">
                <input type="text" id="nicknameInRegistry" placeholder="请输入您的昵称" onblur="nicknameOnBlur()"/>
                <div class="login-center-input-text">昵称</div>
            </div>
        </div>
        <div class="login-center clearfix">
            <div class="login-center-img"><img src="/image/password.png"/></div>
            <div class="login-center-input">
                <input type="password" id="passwordInRegistry" placeholder="请输入8-16位密码" onblur="passwordOnBlur()"/>
                <div class="login-center-input-text">密码</div>
            </div>
        </div>
        <div class="login-center clearfix">
            <div class="login-center-img"><img src="/image/password.png"/></div>
            <div class="login-center-input">
                <input type="password" id="rePassword" placeholder="请确认您的密码" onblur="rePasswordOnBlur()"/>
                <div class="login-center-input-text">确认密码</div>
            </div>
        </div>
        <div class="login-button" id="registryBtn">
            注册
        </div>
        <div style="text-align: center;font-size:15px;margin-top:25px">
            <a href="/login" style="color: #009688;"><< 已有账号，去登录</a>
        </div>
    </div>
</div>
</body>
<script src="/layui/layui.all.js" charset="utf-8"></script>
<script src="/js/login.js"></script>
</html>