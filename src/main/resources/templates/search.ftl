<!doctype html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <title>搜索结果</title>
    <meta name="keywords" content="">
    <meta name="description" content="">
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/css/nprogress.css">
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <link rel="stylesheet" type="text/css" href="/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="/layui/css/layui.css">
    <link rel="shortcut icon" href="/image/shorticon.png">
    <script src="/js/jquery-2.1.4.min.js"></script>
    <script src="/js/nprogress.js"></script>
    <script src="/js/jquery.lazyload.min.js"></script>
</head>
<body class="user-select">
<ul class="layui-nav layui-bg-green">
    <li class="layui-nav-item"><a href="/index">首页</a></li>
    <li class="layui-nav-item"><a href="/index/1">社会新闻</a></li>
    <li class="layui-nav-item"><a href="/index/2">国际新闻</a></li>
    <li class="layui-nav-item"><a href="/index/3">篮球</a></li>
    <li class="layui-nav-item"><a href="/index/4">足球</a></li>
    <div class="widget widget_search" style="width: 300px;position: absolute;top:4px;right:80px">
        <div class="navbar-form">
            <div class="input-group">
                <input type="text" name="keyword" class="form-control" size="35" placeholder="请输入关键字"
                       maxlength="15" autocomplete="off" id="input">
                <span class="input-group-btn">
                    <button class="btn btn-default btn-search" id="search">搜索</button>
                </span>
            </div>
        </div>
    </div>
</ul>
<section class="container">
    <div class="content-wrap">
        <div class="content">
            <div id="content-container">
            <#list articles as article>
                <article class="excerpt excerpt-1" style="">
                    <header>
                        <h2>
                            <a href="/detail/${article.id?c}" title="${article.title}"
                               target="_blank">${article.title}</a>
                        </h2>
                    </header>
                    <p class="meta">
                        <time class="time"><i class="glyphicon glyphicon-time"></i> ${article.time?string('yyyy-MM-dd HH:mm')}</time>
                        <span class="views"><i class="glyphicon glyphicon-eye-open"></i> ${article.browseNumber}</span>
                        <a class="comment" href="##comment" title="评论" target="_blank"><i
                                class="glyphicon glyphicon-comment"></i> ${article.responseNumber}</a>
                    </p>
                    <div class="note"
                         style="display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 3;overflow: hidden;">
                        ${article.brief}
                    </div>
                </article>
            </#list>
            </div>
        </div>
    </div>
</section>

<footer class="footer">
    <div class="container">
        <p>Copyright &copy; Designed by fiole</p>
    </div>
    <div id="gotop" style="display: block;"><a class="gotop" draggable="false"></a></div>
</footer>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/scripts.js"></script>
<script src="/layui/layui.all.js" charset="utf-8"></script>
<script>
    $("#search").click(function () {
        var input = $("#input").val();
        var reg = /\S/;
        if (input === "" || !reg.test(input)){
            layui.use('layer', function(){
                var layer = layui.layer;
                layer.open({
                    type: 4,
                    tips: 4,
                    time: 2000,
                    shade: 0,
                    content: ['请输入您想查询的内容', '#input'] //数组第二项即吸附元素选择器或者DOM
                });
            });
            return;
        }
        var host = window.location.host;
        var protocol = window.location.protocol;
        var url = protocol + "//" + host + "/search?keyword=" + input;
        window.location.href=url;
    });
</script>
</body>
</html>
