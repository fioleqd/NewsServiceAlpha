package com.fiole.newsservicealpha.controller;

import com.fiole.newsservicealpha.entity.Comment;
import com.fiole.newsservicealpha.entity.User;
import com.fiole.newsservicealpha.exception.RequestException;
import com.fiole.newsservicealpha.model.ResponseModel;
import com.fiole.newsservicealpha.model.SubmitCommentRequestModel;
import com.fiole.newsservicealpha.service.ArticleService;
import com.fiole.newsservicealpha.service.CommentService;
import com.fiole.newsservicealpha.util.CookieUtils;
import com.fiole.newsservicealpha.util.Page2ListUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @RequestMapping(value = "/response/ajax/loadMore/{itemId}/{nextPage}",method = RequestMethod.GET)
    @ResponseBody
    public Map loadMoreResponses(@PathVariable("itemId") int itemId,@PathVariable("nextPage") int nextPage){
        Map map = new HashMap();
        Page<Comment> commentsByPaging = commentService.getCommentsByPaging(nextPage - 1, 10, itemId);
        long commentNumbers = commentService.getCommentNumbers(itemId);
        long commentsNOBase = commentNumbers - 10 * (nextPage - 1);
        List<Long> commentsNO = new ArrayList<>();
        List<Comment> comments = Page2ListUtil.page2List(commentsByPaging);
        for (int i = 0;i < comments.size();i++){
            commentsNO.add(commentsNOBase - i);
        }
        map.put("comments",commentsByPaging.iterator());
        map.put("nextPage",nextPage + 1);
        map.put("commentsNO",commentsNO);
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
        int number = articleService.doComment(requestModel.getItemId(), comment);
        if (number == 0){
            log.error("Add comment error, article id is {}",requestModel.getItemId());
            throw new RequestException("系统异常，请重试");
        }
        return ResponseModel.success();
    }
}
