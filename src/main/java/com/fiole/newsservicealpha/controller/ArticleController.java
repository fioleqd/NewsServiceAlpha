package com.fiole.newsservicealpha.controller;

import com.alibaba.fastjson.JSON;
import com.fiole.newsservicealpha.entity.Article;
import com.fiole.newsservicealpha.entity.Comment;
import com.fiole.newsservicealpha.entity.User;
import com.fiole.newsservicealpha.kafka.BrowseProducer;
import com.fiole.newsservicealpha.model.CommentModelDO;
import com.fiole.newsservicealpha.service.ArticleService;
import com.fiole.newsservicealpha.service.CommentService;
import com.fiole.newsservicealpha.Enum.ArticleTypeEnum;
import com.fiole.newsservicealpha.util.CookieUtils;
import com.fiole.newsservicealpha.Enum.ResponseStatusEnum;
import com.fiole.newsservicealpha.util.Page2ListUtil;
import com.fiole.newsservicealpha.util.RedisPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/")
@Slf4j
public class ArticleController {

    @Autowired
    BrowseProducer browseProducer;
    @Autowired
    ArticleService articleService;
    @Autowired
    CookieUtils cookieUtils;
    @Autowired
    CommentService commentService;

    @RequestMapping(method = RequestMethod.GET)
    public String redirect(){
        return "redirect:/index";
    }

    @RequestMapping(value = "index",method = RequestMethod.GET)
    public String showIndex(Model model, HttpServletRequest request){
        User user = cookieUtils.getUser(request);
        if (user == null){
            model.addAttribute("isLogin",false);
        }
        else {
            model.addAttribute("isLogin",true);
            model.addAttribute("user",user);
        }

        List<Article> hotArticles;
        String redisHotKey = "hot:0";
        String redisHotValue = RedisPoolUtil.get(redisHotKey);
        if (redisHotValue == null) {
            Page<Article> hotArticlesPage = articleService.getHotArticles(0, 5, 0);
            hotArticles = Page2ListUtil.page2List(hotArticlesPage);
            redisHotValue = JSON.toJSONString(hotArticles);
            RedisPoolUtil.setEx(redisHotKey,redisHotValue,60 * 60 * 3);
        } else {
            hotArticles = JSON.parseObject(redisHotValue,List.class);
        }

        List<Article> socials;
        String redisSocialKey = "social:0";
        String redisSocialValue = RedisPoolUtil.get(redisSocialKey);
        if (redisSocialValue == null){
            Page<Article> socialsPage = articleService.getArticlesByTypeAndPaging(0, 5,ArticleTypeEnum.SOCIAL.getType(),"id");
            socials = Page2ListUtil.page2List(socialsPage);
            redisSocialValue = JSON.toJSONString(socials);
            RedisPoolUtil.setEx(redisSocialKey,redisSocialValue,60 * 60 * 12);
        } else {
            socials = JSON.parseObject(redisSocialValue,List.class);
        }

        List<Article> worlds;
        String redisWorldKey = "world:0";
        String redisWorldValue = RedisPoolUtil.get(redisWorldKey);
        if (redisWorldValue == null){
            Page<Article> worldsPage = articleService.getArticlesByTypeAndPaging(0, 5,ArticleTypeEnum.WORLD.getType(),"id");
            worlds = Page2ListUtil.page2List(worldsPage);
            redisWorldValue = JSON.toJSONString(worlds);
            RedisPoolUtil.setEx(redisWorldKey,redisWorldValue,60 * 60 * 12);
        } else {
            worlds = JSON.parseObject(redisWorldValue,List.class);
        }

        List<Article> basketballs;
        String redisBasketballKey = "basketball:0";
        String redisBasketballValue = RedisPoolUtil.get(redisBasketballKey);
        if (redisBasketballValue == null){
            Page<Article> basketballsPage = articleService.getArticlesByTypeAndPaging(0, 5, ArticleTypeEnum.BASKETBALL.getType(),"id");
            basketballs = Page2ListUtil.page2List(basketballsPage);
            redisBasketballValue = JSON.toJSONString(basketballs);
            RedisPoolUtil.setEx(redisBasketballKey,redisBasketballValue,60 * 60 * 12);
        } else {
            basketballs = JSON.parseObject(redisBasketballValue,List.class);
        }

        List<Article> soccers;
        String redisSoccerKey = "soccer:0";
        String redisSoccerValue = RedisPoolUtil.get(redisSoccerKey);
        if (redisSoccerValue == null){
            Page<Article> soccersPage = articleService.getArticlesByTypeAndPaging(0,5,ArticleTypeEnum.FOOTBALL.getType(),"id");
            soccers = Page2ListUtil.page2List(soccersPage);
            redisSoccerValue = JSON.toJSONString(soccers);
            RedisPoolUtil.setEx(redisSoccerKey,redisSoccerValue,60 * 60 * 12);
        } else {
            soccers = JSON.parseObject(redisSoccerValue,List.class);
        }
        long articleNumbers = articleService.getArticleNumbers();
        model.addAttribute("socials",socials);
        model.addAttribute("worlds",worlds);
        model.addAttribute("basketballs",basketballs);
        model.addAttribute("soccers",soccers);
        model.addAttribute("articleNumbers",articleNumbers);
        model.addAttribute("type",0);
        model.addAttribute("backToUrl","/index");
        model.addAttribute("pageTitle","首页");
        model.addAttribute("hotArticles", hotArticles);
        return "home";
    }

    @RequestMapping(value = "/index/{type}",method = RequestMethod.GET)
    public String showIndex(Model model, @PathVariable("type") int type, HttpServletRequest request){
        User user = cookieUtils.getUser(request);
        if (user == null){
            model.addAttribute("isLogin",false);
        }else {
            model.addAttribute("isLogin",true);
            model.addAttribute("user",user);
        }
        ArticleTypeEnum typeEnum = ArticleTypeEnum.getByType(type);
        if (typeEnum == null){
            log.error("Wrong request parameter, type is invalid.");
            return "500";
        }
        List<Article> articles;
        String redisFirstPageKey = "firstPage:" + type;
        String redisFirstPageValue = RedisPoolUtil.get(redisFirstPageKey);
        if (redisFirstPageValue == null) {
            Page<Article> articlesPage = articleService.getArticlesByTypeAndPaging(0, 10, type, "id");
            articles = Page2ListUtil.page2List(articlesPage);
            redisFirstPageValue = JSON.toJSONString(articles);
            RedisPoolUtil.setEx(redisFirstPageKey,redisFirstPageValue,60 * 60 * 12);
        } else {
            articles = JSON.parseObject(redisFirstPageValue,List.class);
        }
        long articleNumbers = articleService.getArticleNumbersByType(type);
        List<Article> hotArticles;
        String redisHotKey = "hot:" + type;
        String redisHotValue = RedisPoolUtil.get(redisHotKey);
        if (redisHotValue == null) {
            Page<Article> hotArticlesPage = articleService.getHotArticles(0, 5, type);
            hotArticles = Page2ListUtil.page2List(hotArticlesPage);
            redisHotValue = JSON.toJSONString(hotArticles);
            RedisPoolUtil.setEx(redisHotKey,redisHotValue, 60 * 60 * 3);
        }else {
            hotArticles = JSON.parseObject(redisHotValue,List.class);
        }
        model.addAttribute("articles",articles);
        model.addAttribute("hotArticles",hotArticles);
        model.addAttribute("articleNumbers",articleNumbers);
        model.addAttribute("type",type);
        model.addAttribute("backToUrl","/index/" + type);
        model.addAttribute("pageTitle",typeEnum.getDescription());
        return "home";
    }

    @RequestMapping(value = "/ajax/loadMore/{nextPage}/{type}",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> loadMore(@PathVariable(value = "nextPage") int nextPage, @PathVariable(value = "type") int type){
        ArticleTypeEnum byType = ArticleTypeEnum.getByType(type);
        HashMap<String, Object> map = new HashMap();
        if (byType == null){
            log.warn("Wrong article type");
            map.put("errorCode",ResponseStatusEnum.WrongArticleType.getStatusCode());
            map.put("errorInfo",ResponseStatusEnum.WrongArticleType.getStatusInfo());
            return map;
        }
        Page<Article> articlesByPaging = articleService.getArticlesByTypeAndPaging(nextPage - 1, 10,type,"id");
        map.put("errorCode", ResponseStatusEnum.Success.getStatusCode());
        map.put("errorInfo",ResponseStatusEnum.Success.getStatusInfo());
        map.put("nextPage",nextPage + 1);
        map.put("articles",articlesByPaging.iterator());
        return map;
    }

    @RequestMapping(value = "/detail/{id}")
    public String loadDetail(@PathVariable(value = "id")int id,Model model,HttpServletRequest request){
        User user = cookieUtils.getUser(request);
        if (user == null){
            model.addAttribute("isLogin",false);
        }
        else {
            model.addAttribute("isLogin",true);
            model.addAttribute("user",user);
        }
        Article article = articleService.getArticleById(id);
        if (article == null){
            log.error("Wrong article id");
            return "500";
        }
        HttpSession session = request.getSession();
        Comment commentNew = (Comment)session.getAttribute("commentNew");
        Integer commentNumber = (Integer)session.getAttribute("commentNumber");
        CommentModelDO topComments = (CommentModelDO) session.getAttribute("topComments");
        if(commentNew != null) {
            if (topComments == null) {
                topComments = commentService.getCommentsByPaging(0, 10, id, commentNumber, null);
            }
            else {
                topComments.insertNewComment(commentNew);
            }
            article.setResponseNumber(commentNumber);
            session.setAttribute("commentNew",null);
            session.setAttribute("commentNumber",null);
        }
        else {
            commentNumber = commentService.getCommentNumbers(id);
            topComments = commentService.getCommentsByPaging(0, 10, id, commentNumber,null);
            browseProducer.send(Integer.toString(id));
        }
        session.setAttribute("topComments",topComments);
        Page<Article> hotArticles = articleService.getHotArticles(0, 5, article.getType());
        Page<Article> latestResponseArticles = articleService.getArticlesByTypeAndPaging(0, 5, article.getType(), "updateTime");
        model.addAttribute("article",article);
        model.addAttribute("hotArticles",Page2ListUtil.page2List(hotArticles));
        model.addAttribute("type",article.getType());
        model.addAttribute("count",commentNumber);
        model.addAttribute("commentModelDO", topComments);
        model.addAttribute("backToUrl","/detail/" + id);
        model.addAttribute("latestResponseArticles",Page2ListUtil.page2List(latestResponseArticles));
        return "detail";
    }

    @RequestMapping(value = "/search",method = RequestMethod.GET)
    public String showSearchResult(@RequestParam("keyword") String keyword, Model model){
        Page<Article> searchResult = articleService.getSearchResult(0, 10, keyword);
        model.addAttribute("articles",searchResult.iterator());
        return "search";
    }
}
