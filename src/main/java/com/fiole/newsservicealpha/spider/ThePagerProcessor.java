package com.fiole.newsservicealpha.spider;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.IOException;
import java.util.List;

public class ThePaperProcessor implements PageProcessor {

    WebClient webClient = new WebClient(BrowserVersion.CHROME);

    public ThePaperProcessor(){
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setActiveXNative(false);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        webClient.getOptions().setCssEnabled(false);
    }

    private Site site = Site.me()
            .setRetryTimes(3)
            .setSleepTime(1000 * 5)
            .addHeader("Content-Type","text/plain")
            .addHeader("Origin","https://m.thepaper.cn")
            .addHeader("User-Agent","Mozilla/5.0 (Linux; Android 8.0.0; Pixel 2 XL Build/OPD1.170816.004) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Mobile Safari/537.36");

    public void process(Page page) {
//        processMovie(page);
//        String url = page.getUrl().toString();
//        if(url.startsWith("https://movie.douban.com/j")) {
//            List<String> movieIds = new JsonPathSelector("$.data[*].id").selectList(page.getRawText());
//            String preUrl = new String("https://movie.douban.com/subject/");
//            for(String id : movieIds){
//                page.addTargetRequest(preUrl + id);
//            }
//        }
//        else {
//            page.putField("name",page.getHtml().xpath("//div[@id=content]/h1[1]/span[1]/text()").toString());
//            page.putField("year",page.getHtml().xpath("//div[@id=content]/h1[1]/span[2]/text()").toString());
//            page.putField("director",page.getHtml().xpath("//div[@id=info]/span[1]/span[2]/a/text()").toString());
//            page.putField("score",Double.parseDouble(page.getHtml().xpath("//strong[@property=v:average]/text()").toString()));
//            page.putField("id",Integer.parseInt(url.substring(url.indexOf("subject/") + 8)));
//        }



        String pageUrl = page.getUrl().toString();
        if (pageUrl.contains("newsDetail_forward_")){
            try {
                HtmlPage htmlPage = webClient.getPage(pageUrl);
                DomElement clickForMore = htmlPage.getElementById("clickForMore");
                HtmlPage moreInfoPage = clickForMore.click();
                Thread.sleep(1000);
                List<HtmlElement> byXPath = moreInfoPage.getByXPath("//*[@id=\"v3cont_id\"]/div[1]/div[1]/div/div[1]");
                for (HtmlElement element : byXPath){
                    System.out.println(element.asXml());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else {
            try {
                final String baseUrl = "https://m.thepaper.cn/";
                HtmlPage htmlPage = webClient.getPage(pageUrl);
                DomElement loadnextpage = htmlPage.getElementById("more_bt");
                HtmlPage click = loadnextpage.click();
                Thread.sleep(1000);
                List<HtmlElement> elements = click.getByXPath("//*[@id=\"v3cont_id\"]/div/div/div/div/div/p/a");
                for (HtmlElement element : elements){
                    String linkHref = element.getAttribute("href");
                    if (!linkHref.startsWith("newsDetail_forward_"))
                        continue;
                    page.addTargetRequest(baseUrl + linkHref.trim());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void getAllTargetUrl(Page page) {
        Request[] requests = new Request[3];
        for (int i = 0; i < 3; i++) {
            StringBuilder postUrl = new StringBuilder("https://movie.douban.com/j/new_search_subjects?sort=U&range=0,10&tags=&start=");
            postUrl.append(i * 20);
            requests[i] = new Request(postUrl.toString());
            page.addTargetRequest(requests[i]);
        }
    }

    public Site getSite() {
        return site;
    }

    public void run() {
//        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
//        httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(
//                new Proxy("123.180.211.164",54489),
//                new Proxy("115.223.104.108",31002),
//                new Proxy("125.125.210.27",21414),
//                new Proxy("183.32.216.210",44579),
//                new Proxy("27.152.79.173",22357),
//                new Proxy("180.117.96.37",51580),
//                new Proxy("60.182.188.140",47383),
//                new Proxy("113.57.97.27",37172),
//                new Proxy("36.6.147.179",47491),
//                new Proxy("113.117.195.202",39665)
//        ));


        Spider.create(new ThePaperProcessor()).addPipeline(new ConsolePipeline())
                .addUrl("https://m.thepaper.cn/channel_25950")
                .thread(3)
                .run();
    }
}
