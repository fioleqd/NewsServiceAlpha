<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <script src="/js/layer.js"></script>
    <script src="/layui/layui.js"></script>
    <script src="https://cdn.staticfile.org/jquery/1.10.2/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/layui/css/layui.css"/>
    <title>首页</title>
</head>
<body>
    <div class="layui-container layui-bg-green">
        <div class="layui-col-md4"></div>
        <div class="layui-col-md5">
            <ul class="layui-nav layui-bg-green" lay-filter="">
                <li class="layui-nav-item layui-this"><a href="">新闻</a></li>
                <li class="layui-nav-item"><a href="">IT</a></li>
                <li class="layui-nav-item"><a href="">篮球</a></li>
                <li class="layui-nav-item"><a href="">足球</a></li>
                <li class="layui-nav-item"><a href="">汽车</a></li>
                <li class="layui-nav-item"><i class="layui-icon layui-icon-username"></i></li>
            </ul>
        </div>
        <div class="layui-col-md3">
            <ul class="layui-nav layui-bg-green" lay-filter="">
                <li class="layui-nav-item"><i class="layui-icon layui-icon-username"></i></li>
            </ul>
        </div>
    </div>



</body>
<script>
    //注意：导航 依赖 element 模块，否则无法进行功能性操作
    layui.use('element', function(){
        var element = layui.element;
    });
</script>
</html>