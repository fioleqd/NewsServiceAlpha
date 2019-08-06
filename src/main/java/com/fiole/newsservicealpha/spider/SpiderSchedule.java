package com.fiole.newsservicealpha.spider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import us.codecraft.webmagic.Spider;

@Component
@Configuration
@EnableScheduling
public class SpiderSchedule {

    @Autowired
    ArticlePipeline articlePipeline;

    @Scheduled(cron = "0 30 8/12 * * ? ")
    public void socialSpiderTask(){
        Spider socialSpider = Spider.create(new SocialNewsProcessor()).addPipeline(articlePipeline)
                .addUrl("http://society.people.com.cn/index1.html#fy01")
                .addUrl("http://society.people.com.cn/index2.html#fy01")
                .addUrl("http://society.people.com.cn/index3.html#fy01")
                .thread(1)
                .setExitWhenComplete(true);
        socialSpider.start();
        socialSpider.stop();
    }

    @Scheduled(cron = "0 0 8/12 * * ? ")
    public void basketballSpiderTask(){
        Spider basketballSpider = Spider.create(new BasketballNewsProcessor()).addPipeline(articlePipeline)
                .addUrl("http://sports.people.com.cn/GB/22149/index.html")
                .addUrl("http://sports.people.com.cn/GB/22149/index2.html")
                .thread(3)
                .setExitWhenComplete(true);
        basketballSpider.start();
        basketballSpider.stop();
    }

    @Scheduled(cron = "0 30 9/12 * * ? ")
    public void worldSpiderTask(){
        Spider worldSpider = Spider.create(new WorldNewsProcessor()).addPipeline(articlePipeline)
                .addUrl("http://world.people.com.cn/index1.html#fy01")
                .addUrl("http://world.people.com.cn/index2.html#fy01")
                .thread(3)
                .setExitWhenComplete(true);
        worldSpider.start();
        worldSpider.stop();
    }

    @Scheduled(cron = "0 0 9/12 * * ? ")
    public void soccerSpiderTask(){
        Spider soccerSpider = Spider.create(new SoccerNewsSpider()).addPipeline(articlePipeline)
                .addUrl("http://sports.people.com.cn/GB/22134/index.html")
                .addUrl("http://sports.people.com.cn/GB/22141/index.html")
                .thread(3)
                .setExitWhenComplete(true);
        soccerSpider.start();
        soccerSpider.stop();
    }
}
