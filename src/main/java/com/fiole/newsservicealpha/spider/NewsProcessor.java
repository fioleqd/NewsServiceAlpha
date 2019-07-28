package com.fiole.newsservicealpha.spider;

import com.fiole.newsservicealpha.util.ArticleTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

@Component
public class NewsProcessor implements PageProcessor {
    private Site site = Site.me()
            .setRetryTimes(3)
            .setSleepTime(1000 * 5)
            .addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 8.0.0; Pixel 2 XL Build/OPD1.170816.004) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Mobile Safari/537.36");

    public void process(Page page) {
        String pageUrl = page.getUrl().toString();
        String baseUrl = "http://society.people.com.cn";
        if (pageUrl.endsWith("#fy01")){
            Elements newsList = page.getHtml().getDocument().getElementsByClass("hdNews clearfix");
            for (Element e : newsList){
                Element linkTag = e.child(0).child(0).child(0);
                String suffixUrl = linkTag.attr("href");
                page.addTargetRequest(baseUrl + suffixUrl);
            }
        }
        else {
            Document document = page.getHtml().getDocument();
            Elements h1 = document.getElementsByTag("h1");
            page.putField("title",h1.get(0).text());
            Elements sentenceContent = document.getElementById("rwb_zw").getElementsByTag("p");
            StringBuilder sb = new StringBuilder();
            for (Element e : sentenceContent){
                Elements children = e.children();
                boolean isRecreated = false;
                if (children.size() > 0){
                    Element element = children.get(0);
                    String src = element.attr("src");
                    if (!StringUtils.isEmpty(src)){
                        int indexOfDot = src.indexOf('.');
                        String filePath =  "C:\\imagesFromUrl\\" + Thread.currentThread().getId() + System.currentTimeMillis()
                                + src.substring(indexOfDot);
                        try {
                            URL url = new URL(baseUrl + src);
                            BufferedImage img = ImageIO.read(url);
                            ImageIO.write(img, src.substring(indexOfDot + 1), new File(filePath));
                            sb.append("<p>" + "<img src=\"").append(filePath).append("\"></img>").append("</p>");
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }finally {
                            isRecreated = true;
                        }
                    }
                }
                if (!isRecreated)
                    sb.append("<p>").append(e.html()).append("</p>");
            }
            page.putField("content",sb.toString());
            Elements f1 = page.getHtml().getDocument().getElementsByClass("fl");
            page.putField("time",f1.get(2).text().substring(0,17));
            page.putField("source",f1.get(2).child(0).text());
            page.putField("type", ArticleTypeEnum.NEWS.getType());
        }
    }

    public Site getSite() {
        return site;
    }
}
