package com.fiole.newsservicealpha.spider;

import com.fiole.newsservicealpha.Enum.ArticleTypeEnum;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class BasketballNewsProcessor implements PageProcessor {

    private Site site = Site.me()
            .setRetryTimes(3)
            .setSleepTime(1000 * 20)
            .addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 8.0.0; Pixel 2 XL Build/OPD1.170816.004) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Mobile Safari/537.36");

    public void process(Page page) {
        String pageUrl = page.getUrl().toString();
        PeopleNewsExtract peopleNewsExtract = new PeopleNewsExtract();
        if (pageUrl.contains("/GB/22149")){
            peopleNewsExtract.addAllTargetLink(page,"hdNews clearfix");
            page.putField("pageType",1);
            page.putField("url",pageUrl);
        }
        else {
            peopleNewsExtract.pageExtract(page, ArticleTypeEnum.BASKETBALL);
        }
    }
    public Site getSite() {
        return site;
    }
}
