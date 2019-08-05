package com.fiole.newsservicealpha.util;

import com.alibaba.fastjson.JSON;
import com.fiole.newsservicealpha.entity.Token;
import com.fiole.newsservicealpha.entity.User;
import com.fiole.newsservicealpha.service.TokenService;
import com.fiole.newsservicealpha.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
@Slf4j
public class CookieUtils {
    @Autowired
    TokenService tokenService;
    @Autowired
    UserService userService;
    public User getUser(HttpServletRequest request){
        User user = null;
        Cookie[] cookies = request.getCookies();
        if (cookies == null)
            return null;
        String uuidToken = null;
        String userId = null;
        for (Cookie cookie : cookies){
            if ("token".equals(cookie.getName())){
                String cookieToken = cookie.getValue();
                uuidToken = cookieToken.substring(0,cookieToken.indexOf('#'));
                userId = cookieToken.substring(cookieToken.indexOf('#') + 1);
                break;
            }
        }
        int id = 0;
        try{
            id = Integer.parseInt(userId);
        }catch (Exception e){
            log.error("Parse integer failed!");
        }
        String redisTokenKey = "user.token:" + id;
        String redisTokenValue = RedisPoolUtil.get(redisTokenKey);
        Token token = null;
        long expire = 0;
        if (redisTokenValue == null) {
             token = tokenService.getToken(id, uuidToken);
            if (token == null)
                return null;
            Date date = new Date();
            if (!token.getInvalidTime().after(date))
                return null;
            Date invalidTime = token.getInvalidTime();
            expire = (invalidTime.getTime() - date.getTime()) / 1000;
            String tokenJson = JSON.toJSONString(token);
            RedisPoolUtil.setEx(redisTokenKey,tokenJson,(int) expire);
        } else {
            token = JSON.parseObject(redisTokenValue,Token.class);
            if(!StringUtils.equals(uuidToken,token.getToken())){
                log.warn("Token:{} in redis is wrong",token.getToken());
                RedisPoolUtil.del(redisTokenKey);
            }
            if (token.getInvalidTime().before(new Date())){
                log.warn("Redis token:{} is out of date",token.getToken());
                RedisPoolUtil.del(redisTokenKey);
            }
        }
        String redisUserKey = "user:" + id;
        String redisUserValue = RedisPoolUtil.get(redisUserKey);
        if (redisUserValue == null) {
            user = userService.getUserById(id);
            String userJson = JSON.toJSONString(user);
            RedisPoolUtil.setEx(redisUserKey,userJson,(int) expire);
        } else {
            user = JSON.parseObject(redisUserValue,User.class);
        }
        return user;
    }
}
