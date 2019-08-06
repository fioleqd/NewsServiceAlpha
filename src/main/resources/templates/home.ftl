<!doctype html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <title>${pageTitle}</title>
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
<input style="display: none" value="${articleNumbers}" id="count"/>
<input style="display: none" value="${type}" id="type"/>
<body class="user-select">
<header class="header">
    <nav class="navbar navbar-default" id="navbar">
        <div class="container">
            <ul class="layui-nav nav navbar-nav" style="background-color: white;color: lightslategray">
                <li class="layui-nav-item <#if type == 0>layui-this</#if>"><a data-cont="首页" title="首页"
                                                                              href="/index">首页</a></li>
                <li class="layui-nav-item <#if type == 1>layui-this</#if>"><a data-cont="社会新闻" title="社会新闻"
                                                                              href="/index/1">社会新闻</a></li>
                <li class="layui-nav-item <#if type == 2>layui-this</#if>"><a data-cont="国际新闻" title="国际新闻" href="/index/2">国际新闻</a>
                </li>
                <li class="layui-nav-item <#if type == 3>layui-this</#if>"><a data-cont="篮球" title="篮球" href="/index/3">篮球</a>
                </li>
                <li class="layui-nav-item <#if type == 4>layui-this</#if>"><a data-cont="足球" title="足球" href="/index/4">足球</a>
                </li>
            </ul>
            <ul class="layui-nav navbar-nav navbar-right" style="background-color: white;color: lightslategray">
                    <#if isLogin>
                        <li class="layui-nav-item">
                            <a href="javascript:void(0)"><img src="${user.avatar}"/></a>
                            <dl class="layui-nav-child layui-anim layui-anim-upbit">
                                <dd><a id="logoutBtn">退出登录 <i class="glyphicon glyphicon-off"></i></a></dd>
                            </dl>
                        </li>
                    <#else>
                        <li class="layui-nav-item"><a data-cont="登录" title="登录" href="/login?backToUrl=${backToUrl}">登录</a></li>
                        <li class="layui-nav-item"><a data-cont="注册" title="注册" href="/registry">注册</a></li>
                    </#if>
            </ul>
        </div>
    </nav>
</header>
<section class="container">
    <div class="content-wrap">
        <div class="content">
            <#if type == 0>
            <div id="focusslide" class="carousel slide" data-ride="carousel">
                <ol class="carousel-indicators">
                    <li data-target="#focusslide" data-slide-to="0" class="active"></li>
                    <li data-target="#focusslide" data-slide-to="1"></li>
                    <li data-target="#focusslide" data-slide-to="2"></li>
                    <li data-target="#focusslide" data-slide-to="3"></li>
                </ol>
                <div class="carousel-inner" role="listbox">
                    <div class="item active">
                        <a href="/index/1" target="_blank">
                            <img src="/image/world.png" class="img-responsive"></a>
                    </div>
                    <div class="item">
                        <a href="/index/2" target="_blank">
                            <img src="/image/world.png" class="img-responsive"></a>
                    </div>
                    <div class="item">
                        <a href="/index/3" target="_blank">
                            <img src="/image/soccer.png" class="img-responsive"></a>
                    </div>
                    <div class="item">
                        <a href="/index/4" target="_blank">
                            <img src="/image/soccer.png" class="img-responsive"></a>
                    </div>
                </div>
                <a class="left carousel-control" href="#focusslide" role="button" data-slide="prev" rel="nofollow">
                    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                    <span class="sr-only">上一个</span>
                </a>
                <a class="right carousel-control" href="#focusslide" role="button" data-slide="next" rel="nofollow">
                    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                    <span class="sr-only">下一个</span>
                </a>
            </div>
            </#if>
            <#if type != 0>
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
                        <time class="time"><i class="glyphicon glyphicon-time"></i> ${article.time}</time>
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
            <div id="demo0" style="<#if articleNumbers <= 10>display:none;</#if>text-align:center"></div>
            <#else >
                <fieldset class="layui-elem-field layui-field-title">
                    <legend style="color: #777">社会新闻</legend>
                </fieldset>
                <#list socials as article>
                <article class="excerpt excerpt-1" style="">
                    <header>
                        <h2>
                            <a href="/detail/${article.id?c}" title="${article.title}"
                               target="_blank">${article.title}</a>
                        </h2>
                    </header>
                    <p class="meta">
                        <time class="time"><i class="glyphicon glyphicon-time"></i> ${article.time}</time>
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
                <fieldset class="layui-elem-field layui-field-title">
                    <legend style="color: #777">国际新闻</legend>
                </fieldset>
                <#list worlds as article>
                <article class="excerpt excerpt-1" style="">
                    <header>
                        <h2>
                            <a href="/detail/${article.id?c}" title="${article.title}"
                               target="_blank">${article.title}</a>
                        </h2>
                    </header>
                    <p class="meta">
                        <time class="time"><i class="glyphicon glyphicon-time"></i> ${article.time}</time>
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
                <fieldset class="layui-elem-field layui-field-title">
                    <legend style="color: #777">篮球</legend>
                </fieldset>
                <#list basketballs as article>
                <article class="excerpt excerpt-1" style="">
                    <header>
                        <h2>
                            <a href="/detail/${article.id?c}" title="${article.title}"
                               target="_blank">${article.title}</a>
                        </h2>
                    </header>
                    <p class="meta">
                        <time class="time"><i class="glyphicon glyphicon-time"></i> ${article.time}</time>
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
                <fieldset class="layui-elem-field layui-field-title">
                    <legend style="color: #777">足球</legend>
                </fieldset>
                <#list soccers as article>
                <article class="excerpt excerpt-1" style="">
                    <header>
                        <h2>
                            <a href="/detail/${article.id?c}" title="${article.title}"
                               target="_blank">${article.title}</a>
                        </h2>
                    </header>
                    <p class="meta">
                        <time class="time"><i class="glyphicon glyphicon-time"></i> ${article.time}</time>
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
            </#if>
        </div>
    </div>
    <aside class="sidebar">
        <div class="fixed" style="">
            <div class="widget widget_search">
                <div class="navbar-form">
                    <div class="input-group">
                        <input type="text" name="keyword" class="form-control" size="35" placeholder="请输入关键字"
                               maxlength="15" autocomplete="off" id="input">
                        <span class="input-group-btn">
		<button class="btn btn-default btn-search" id="search">搜索</button>
		</span></div>
                </div>
            </div>
            <#if hotArticles?size gt 0>
            <div class="widget widget_hot">
                <h3>最热新闻 <i class="glyphicon glyphicon-fire"></i></h3>
                <ul>
                    <#list hotArticles as article>
                    <li><a title="${article.title}" href="/detail/${article.id?c}"><span class="thumbnail">
			</span><span class="text">${article.title}</span><span class="muted"><i
                            class="glyphicon glyphicon-time"></i>
				${article.time}
			</span><span class="muted"><i class="glyphicon glyphicon-eye-open"></i> ${article.browseNumber}</span></a></li>
                    </#list>
                </ul>
            </div>
            </#if>
        </div>
    </aside>
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
<script src="/js/login.js"></script>
<script src="/js/my.js"></script>
</body>
</html>
