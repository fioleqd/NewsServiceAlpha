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
        User user;
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
        String redisTokenKey = "user:" + uuidToken + "#" + id;
        String redisTokenValue = RedisPoolUtil.get(redisTokenKey);
        Token token;
        if (redisTokenValue == null) {
            token = tokenService.getToken(id, uuidToken);
            if (token == null)
                return null;
            user = JSON.parseObject(token.getUserInfo(),User.class);
            if (user == null){
                user = userService.getUserById(id);
            }
            if (user == null){
                return null;
            }
            Date invalidTime = token.getInvalidTime();
            long expire = (invalidTime.getTime() - new Date().getTime()) / 1000;
            String tokenJson = JSON.toJSONString(token);
            RedisPoolUtil.setEx(redisTokenKey,tokenJson,(int) expire);
        } else {
            token = JSON.parseObject(redisTokenValue,Token.class);
            user = JSON.parseObject(token.getUserInfo(),User.class);
        }
        return user;
    }
}
