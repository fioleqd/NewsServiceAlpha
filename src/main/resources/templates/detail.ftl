<!doctype html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <title>${article.title}</title>
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

<body class="user-select single">
<input style="display: none" id="count" value="${count}"/>
<input style="display: none" id="isLogin" value="${isLogin?string('true', 'false')}"/>
<input style="display: none" id="itemId" value="${article.id?c}">
<#if isLogin>
<input style="display: none" id="userId" value="${user.id?c}">
<input style="display: none" id="nickname" value="${user.nickname}"/>
</#if>
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
                                <dd><a id="logoutBtn"><i class="glyphicon glyphicon-off"></i> 退出登录</a></dd>
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
            <header class="article-header">
                <h1 class="article-title">${article.title}</h1>
                <div class="article-meta">
		<span class="item article-meta-time">
	  		<time class="time" data-toggle="tooltip" data-placement="bottom" title=""
                  data-original-title="发表时间：${article.time}"><i class="glyphicon glyphicon-time"></i> ${article.time}</time>
		</span>
                    <span class="item article-meta-source" data-toggle="tooltip" data-placement="bottom" title=""
                          data-original-title="来源：${article.source}"><i
                            class="glyphicon glyphicon-globe"></i> ${article.source}</span> <span
                        class="item article-meta-views" data-toggle="tooltip" data-placement="bottom" title=""
                        data-original-title="浏览量：${article.browseNumber}"><i
                        class="glyphicon glyphicon-eye-open"></i> ${article.browseNumber}</span> <span
                        class="item article-meta-comment" data-toggle="tooltip" data-placement="bottom" title=""
                        data-original-title="评论量"><i class="glyphicon glyphicon-comment"></i> ${article.responseNumber}</span>
                </div>
            </header>
            <article class="article-content">
            ${article.content}
            </article>
            <div class="article-tags">
            </div>

            <div class="title" id="comment">
                <h3>评论</h3>
            </div>
            <div id="respond">
            <div class="comment">
                <div class="comment-box">
                            <textarea placeholder="您的评论或留言" name="comment-textarea" id="comment-textarea"
                                      cols="100%" rows="3" tabindex="3" <#if !isLogin>disabled</#if>></textarea>
                    <div class="comment-ctrl">
                        <button type="submit" name="comment-submit" id="comment-submit" tabindex="4">评论</button>
                    </div>
                </div>
            </div>
        </div>
            <div id="postcomments">
                <#if count != 0>
                <div id="comments">
                    <ol id="comment_list" class="commentlist">
                        <#list comments as comment>
                        <li class="comment-content"><span class="comment-f">#${commentsNO[comment_index]}</span>
                            <div class="comment-main">
                                <p>
                                    <a class="address" href="javascript:void(0);" rel="nofollow">${comment.nickname}</a>
                                    <span class="time">(${comment.createTime?string('yyyy-MM-dd HH:mm')})</span>
                                    <pre>${comment.content}</pre>
                                </p>
                            </div>
                        </li>
                        </#list>
                    </ol>
                </div>
                <#else >
                <div style="width:100%;color: lightslategray;text-align: center">
                    <img src="/image/comments.jpg" style="width: 100px;"><br>
                    暂无评论
                </div>
                </#if>
                <div id="pageInDetail" style="<#if count <= 10>display:none;</#if>text-align:center"></div>
            </div>
        </div>
    </div>
    <aside class="sidebar">
        <div class="fixed">
            <div class="widget widget_search">
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
            <#if latestResponseArticles?size gt 0>
            <div class="widget widget_hot">
                <h3>最新评论文章</h3>
                <ul>
                    <#list latestResponseArticles as article>
                    <li>
                        <a title="${article.title}" href="/detail/${article.id?c}">
                            <span class="text">${article.title}</span>
                            <span class="muted"><i class="glyphicon glyphicon-time"></i> ${article.time}</span>
                            <span class="muted"><i class="glyphicon glyphicon-comment"></i> ${article.responseNumber}</span>
                        </a>
                    </li>
                    </#list>
                </ul>
            </div>
            </#if>
        </div>
    </aside>
</section>
<footer class="footer">
    <div class="container">
        <p>Copyright &copy; Designed By fiole</p>
    </div>
    <div id="gotop" style="display: block;"><a class="gotop" draggable="false"></a></div>
</footer>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/scripts.js"></script>
<script src="/js/login.js"></script>
<script src="/layui/layui.all.js" charset="utf-8"></script>
<script src="/js/detail.js"></script>
</body>
</html>
