package com.fiole.newsservicealpha.spider;

import com.fiole.newsservicealpha.Enum.ArticleTypeEnum;
import com.fiole.newsservicealpha.util.RedisPoolUtil;
import com.fiole.newsservicealpha.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;

import java.util.Date;

@Slf4j
public class PeopleNewsExtract {
    public void pageExtract(Page page, ArticleTypeEnum typeEnum){
        Document document = page.getHtml().getDocument();
        String pageUrl = page.getUrl().toString();
        Elements h1 = document.getElementsByTag("h1");
        page.putField("pageType",2);
        page.putField("url",pageUrl);
        page.putField("title",h1.get(0).text());
        Element contentBlock = document.getElementById("rwb_zw");
        if (contentBlock == null){
            log.warn("Skip this page, the page structure can't get the content by same method");
            page.putField("skip",Boolean.TRUE);
            return;
        }
        Elements sentenceContent = contentBlock.getElementsByTag("p");
        if (sentenceContent == null){
            log.warn("Skip this page, the page structure can't get the content by same method");
            page.putField("skip",Boolean.TRUE);
            return;
        }
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (Element pTag : sentenceContent){
            String regex = "[\\s]*";
            if (pTag.hasClass("pictext"))
                continue;
            if (pTag.text().matches(regex))
                continue;
            if (isFirst){
                page.putField("brief",pTag.text().trim().replaceAll("　",""));
                isFirst = false;
            }
            sb.append("<p>");
            sb.append(pTag.text().trim().replaceAll("　",""));
            sb.append("</p>");
        }
        if (sb.toString().isEmpty()){
            log.info("Skip this page, the page content is empty");
            page.putField("skip",Boolean.TRUE);
            return;
        }
        page.putField("content",sb.toString());
        Element f1 = page.getHtml().getDocument().getElementsByClass("fl").get(2);
        Date date = null;
        String source = "人民网";
        if (f1 != null) {
            date = TimeUtil.string2Date(f1.text().substring(0, 17));
            if (f1.childNodeSize() > 0){
                source = f1.child(0).text();
            }
        }
        else {
            date = new Date();
        }
        Date todayZeroOClock = TimeUtil.getTodayZeroOClock();
        if (date.before(todayZeroOClock)){
            page.putField("skip",Boolean.TRUE);
            return;
        }
        page.putField("time",date);
        page.putField("source",source);
        page.putField("type", typeEnum.getType());
        page.putField("skip",Boolean.FALSE);
    }

    public void addAllTargetLink(Page page,String className){
        String pageUrl = page.getUrl().toString();
        String baseUrl = pageUrl.substring(0,pageUrl.indexOf(".com.cn") + 7);
        Elements newsList = page.getHtml().getDocument().getElementsByClass(className);
        String url;
        for (Element e : newsList){
            Element linkTag = e.child(0).getElementsByTag("a").first();
            String suffixUrl = linkTag.attr("href");
            if (!suffixUrl.startsWith("http://") && !suffixUrl.startsWith("https://"))
                url = baseUrl + suffixUrl;
            else url = suffixUrl;
            String redisUrlValue = RedisPoolUtil.get(url);
            if (redisUrlValue == null){
                RedisPoolUtil.setEx(url,"1",60 * 60 * 13);
                page.addTargetRequest(url);
            }
        }
    }
}
