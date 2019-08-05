layui.use(['laypage', 'layer'], function(){
    var count = $("#count").attr("value");
    var laypage = layui.laypage;
    var type = $("#type").attr("value");
    //自定义样式
    laypage.render({
        elem: 'demo0'
        ,count: count
        ,theme: '#009688'
        ,jump: function(obj,first){
            //模拟渲染
            if (!first){
                $.ajax({
                    url: "/ajax/loadMore/" + obj.curr + "/" + type,
                    async: true,
                    type: "GET",
                    dataType: "json",
                    success: function (resp) {
                        $("#content-container").html("");
                        resp.articles.forEach(function (item) {
                            var article = "<article class=\"excerpt excerpt-1\" style=\"\">\n" +
                                "                <header>\n" +
                                "                    <h2>\n" +
                                "                        <a href=\"/detail/" + item.id +"\" title=\"" + item.title + "\" target=\"_blank\" >" + item.title + "</a>\n" +
                                "                    </h2>\n" +
                                "                </header>\n" +
                                "                <p class=\"meta\">\n" +
                                "                    <time class=\"time\"><i class=\"glyphicon glyphicon-time\"></i> " + getLocalTime(item.time) + "</time>\n" +
                                "                    <span class=\"views\"><i class=\"glyphicon glyphicon-eye-open\"></i> " + item.browseNumber + "</span> <a class=\"comment\" href=\"##comment\" title=\"评论\" target=\"_blank\" ><i class=\"glyphicon glyphicon-comment\"></i> " + item.responseNumber + "</a>\n" +
                                "                </p>\n" +
                                "                <div class=\"note\" style=\"display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 3;overflow: hidden;\">"+ item.brief +"\n" +
                                "                </div>\n" +
                                "            </article>"
                            $("#content-container").append(article);
                        })
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


