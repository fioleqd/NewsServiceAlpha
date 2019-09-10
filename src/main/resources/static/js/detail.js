$("#comment-submit").click(function () {
    var isLogin = $("#isLogin").attr("value");
    if (isLogin === "false"){
        return;
    }
    var reg = /\S/;
    var inputComment = $("#comment-textarea").val();
    if (!reg.test(inputComment)){
        layui.use('layer', function(){
            var layer = layui.layer;
            layer.msg("请留下你的文字吧，不要惜字如金哦");
        });
        return;
    }
    var data = {};
    var userId = $("#userId").attr("value");
    var itemId = $("#itemId").attr("value");
    var nickname = $("#nickname").attr("value");
    var commentsNumber = $("#commentsNumber").attr("value");
    data.userId = userId;
    data.itemId = itemId;
    data.content = inputComment;
    data.nickname = nickname;
    data.commentsNumber = commentsNumber;
    var requestModel = JSON.stringify(data);
    $.ajax({
        url: "/repsonse/ajax/submit",
        async: false,
        type: "POST",
        dataType: "json",
        contentType : "application/json",
        data: requestModel,
        success: function (resp) {
            if (resp.errorCode === "0") {
                var host = window.location.host;
                var protocol = window.location.protocol;
                var url = protocol + "//" + host + "/detail/" + itemId;
                window.location.href=url;
            }
        }
    });
});

layui.use(['laypage', 'layer'], function(){
    var count = $("#count").attr("value");
    var laypage = layui.laypage;
    var itemId = $("#itemId").attr("value");
    //自定义样式
    laypage.render({
        elem: 'pageInDetail'
        ,count: count
        ,theme: '#009688'
        ,jump: function(obj,first){
            //模拟渲染
            if (!first){
                $.ajax({
                    url: "/response/ajax/loadMore/" + itemId + "/" + obj.curr,
                    async: true,
                    type: "GET",
                    dataType: "json",
                    success: function (resp) {
                        $("#comments").html("");
                        var comments = "<ol id=\"comment_list\" class=\"commentlist\">";
                        for (var i = 0; i < resp.commentModelDO.commentModels.length; i++) {
                            var comment = resp.commentModelDO.commentModels[i].comment;
                            var commentNo = resp.commentModelDO.commentModels[i].commentsNo;
                            comments += "<li class=\"comment-content\"><span class=\"comment-f\">#" + commentNo + "</span>\n" +
                                "                            <div class=\"comment-main\"><p><a class=\"address\" href=\"#\" rel=\"nofollow\"\n" +
                                "                                                            target=\"_blank\">" + comment.nickname + "</a><span class=\"time\">(" + getLocalTime(comment.createTime) + ")</span><br>" + comment.content + "\n" +
                                "                            </p></div>\n" +
                                "                        </li>";
                        }
                        comments += "</ol>";
                        $("#comments").append(comments);
                    }
                });
            }
        }
    });
});

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

function getLocalTime(nS) {
    var date = new Date(nS);
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var day = date.getDate();
    var minute = date.getMinutes();
    var hour = date.getHours();
    return year + "-" + month + "-" + day + " " + (hour > 10 ? hour : '0' + hour) + ":" + (minute > 10 ? minute : '0' + minute);
}
