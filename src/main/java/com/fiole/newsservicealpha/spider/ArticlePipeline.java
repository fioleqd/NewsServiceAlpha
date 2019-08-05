package com.fiole.newsservicealpha.spider;

import com.fiole.newsservicealpha.entity.Article;
import com.fiole.newsservicealpha.service.ArticleService;
import com.fiole.newsservicealpha.util.RedisPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Date;

@Component
@Slf4j
public class ArticlePipeline implements Pipeline {

    @Autowired
    ArticleService articleService;

    @Override
    public void process(ResultItems resultItems, Task task) {
        Integer pageType = resultItems.get("pageType");
        if (pageType == 1){
            log.info("download list page, url is {}",(String)resultItems.get("url"));
        }
        else if(pageType == 2){
            Boolean skip = resultItems.get("skip");
            if (skip) {
                return;
            }
            String url = resultItems.get("url");
            if (RedisPoolUtil.get(url) == null){
                RedisPoolUtil.setEx(url,"exist",3600);
            }
            else {
                log.warn("This page:{} has stored",url);
                return;
            }
            log.info("download detail page, url is {}", url);
            Article article = new Article();
            article.setBrowseNumber(0);
            article.setContent(resultItems.get("content"));
            long now = System.currentTimeMillis();
            article.setResponseNumber(0);
            article.setType(resultItems.get("type"));
            article.setBrief(resultItems.get("brief"));
            article.setSource(resultItems.get("source"));
            article.setState(1);
            article.setTime(resultItems.get("time"));
            article.setTitle(resultItems.get("title"));
            article.setCreateTime(new Date(now));
            article.setUpdateTime(new Date(now));
            articleService.saveArticle(article);
        } else {
            log.error("Wrong page url");
        }
    }
}
