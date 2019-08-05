<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>登录</title>
    <meta name="description" content="">
    <meta name="keywords" content="">
    <link rel="stylesheet" media="screen" href="/css/loginstyle.css">
    <link rel="stylesheet" type="text/css" href="/css/reset.css"/>
    <link rel="shortcut icon" href="/image/shorticon.png">
    <script src="/js/jquery-2.1.4.min.js"></script>
</head>
<body>
<input style="display: none" id="back" value="${backToUrl}"/>
<div id="particles-js">
    <div class="login">
        <div class="login-top">
            登录
        </div>
        <div class="login-center clearfix">
            <div class="login-center-img"><img src="image/name.png"/></div>
            <div class="login-center-input">
                <input type="text" id="username" placeholder="请输入您的用户名" onblur="onBlur(this)" which="1"/>
                <div class="login-center-input-text">用户名</div>
            </div>
        </div>
        <div class="login-center clearfix">
            <div class="login-center-img"><img src="image/password.png"/></div>
            <div class="login-center-input">
                <input type="password" id="password" placeholder="请输入您的密码" onblur="onBlur(this)" which="2"/>
                <div class="login-center-input-text">密码</div>
            </div>
        </div>
        <div class="login-button" id="loginBtn">
            登录
        </div>
        <div style="text-align: center;font-size:15px;margin-top:25px">
            <a href="/registry" style="color: #009688;">没有账号，去注册 >></a>
        </div>
    </div>
</div>
</body>
<script src="/js/login.js"></script>
</html>