package com.fiole.newsservicealpha.controller;

import com.fiole.newsservicealpha.entity.Comment;
import com.fiole.newsservicealpha.entity.User;
import com.fiole.newsservicealpha.exception.RequestException;
import com.fiole.newsservicealpha.kafka.CommentsProducer;
import com.fiole.newsservicealpha.model.CommentModelDO;
import com.fiole.newsservicealpha.model.ResponseModel;
import com.fiole.newsservicealpha.model.SubmitCommentRequestModel;
import com.fiole.newsservicealpha.service.ArticleService;
import com.fiole.newsservicealpha.service.CommentService;
import com.fiole.newsservicealpha.util.CookieUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.*;

@Controller
@RequestMapping
@Slf4j
public class CommentsController {
    @Autowired
    CommentService commentService;
    @Autowired
    CookieUtils cookieUtils;
    @Autowired
    ArticleService articleService;
    @Autowired
    CommentsProducer commentsProducer;

    @RequestMapping(value = "/response/ajax/loadMore/{itemId}/{nextPage}",method = RequestMethod.GET)
    @ResponseBody
    public Map loadMoreResponses(@PathVariable("itemId") int itemId,@PathVariable("nextPage") int nextPage){
        Map map = new HashMap();
        int commentNumbers = commentService.getCommentNumbers(itemId);
        CommentModelDO commentModelDO = commentService.getCommentsByPaging(nextPage - 1, 10, itemId,
                commentNumbers - (nextPage - 1) * 10, null);
        map.put("nextPage",nextPage + 1);
        map.put("commentModelDO",commentModelDO);
        return map;
    }

    @RequestMapping(value = "/repsonse/ajax/submit",method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel submit(@RequestBody SubmitCommentRequestModel requestModel, HttpServletRequest request){
        User user = cookieUtils.getUser(request);
        if (user == null){
            log.warn("Submit comment but user is null");
            throw new RequestException("获取用户状态失败，请重新登录");
        }
        if (user.getId() != requestModel.getUserId()){
            log.warn("Submit user is not online user, online user id is " + user.getId() + ", request user id is " + requestModel.getUserId());
            throw new RequestException("用户身份验证失败，请重试");
        }
        Comment comment = new Comment();
        long now = System.currentTimeMillis();
        String handledContent = requestModel.getContent()
                .replaceAll("<","&lt;")
                .replaceAll(">","&gt;");
        comment.setContent(handledContent);
        comment.setItemId(requestModel.getItemId());
        comment.setUserId(requestModel.getUserId());
        comment.setNickname(requestModel.getNickname());
        comment.setParentId(0);
        comment.setState(1);
        comment.setUpdateTime(new Date(now));
        comment.setCreateTime(new Date(now));
        commentsProducer.send(comment);
        HttpSession session = request.getSession();
        session.setAttribute("commentNew",comment);
        session.setAttribute("commentNumber",requestModel.getCommentsNumber() + 1);
        return ResponseModel.success();
    }
}
