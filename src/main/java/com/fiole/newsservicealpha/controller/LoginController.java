package com.fiole.newsservicealpha.controller;

import com.fiole.newsservicealpha.entity.Token;
import com.fiole.newsservicealpha.entity.User;
import com.fiole.newsservicealpha.service.TokenService;
import com.fiole.newsservicealpha.service.UserService;
import com.fiole.newsservicealpha.util.CookieUtils;
import com.fiole.newsservicealpha.Enum.ResponseStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping
@Slf4j
public class LoginController {

    @Autowired
    UserService userService;
    @Autowired
    TokenService tokenService;
    @Autowired
    CookieUtils cookieUtils;

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String returnLogin(@RequestParam("backToUrl") @Nullable String backToUrl, Model model){
        if (StringUtils.isEmpty(backToUrl)){
            backToUrl = "/index";
        }
        model.addAttribute("backToUrl",backToUrl);
        return "login";
    }

    @RequestMapping(value = "/login/ajaxVerify",method = RequestMethod.POST)
    @ResponseBody
    public Map verifyUser(@RequestBody Map requestModel, HttpServletRequest request, HttpServletResponse response){
        Map map = new HashMap();
        User user = cookieUtils.getUser(request);
        if (user != null){
            log.warn("User is online");
            map.put("errorCode",ResponseStatusEnum.LoginRepeat.getStatusCode());
            map.put("errorInfo",ResponseStatusEnum.LoginRepeat.getStatusInfo());
            return map;
        }
        String username = (String) requestModel.get("username");
        String password = (String) requestModel.get("password");
        if(username == null || password == null){
            map.put("errorCode", ResponseStatusEnum.LoginInvalidParameters.getStatusCode());
            map.put("errorInfo",ResponseStatusEnum.LoginInvalidParameters.getStatusInfo());
            log.warn("Login verify parameters null");
        }
        user = userService.getUserByUsernameAndPassword(username, password);
        if (user == null){
            map.put("errorCode",ResponseStatusEnum.LoginInvalidUser.getStatusCode());
            map.put("errorInfo",ResponseStatusEnum.LoginInvalidUser.getStatusInfo());
            log.info("User not exist");
        }
        else {
            String uuidToken = UUID.randomUUID().toString();
            log.info("User is " + user.getId() + ", token is " + uuidToken);
            Token token = new Token();
            token.setUserId(user.getId());
            token.setToken(uuidToken);
            long now = System.currentTimeMillis();
            long invalidTime = now + 1000 * 60 * 60 * 24 * 7;
            token.setInvalidTime(new Date(invalidTime));
            token.setState(1);
            tokenService.saveToken(token);
            map.put("errorCode",ResponseStatusEnum.Success.getStatusCode());
            map.put("errorInfo",ResponseStatusEnum.Success.getStatusInfo());
            Cookie cookie = new Cookie("token",uuidToken + "#" + user.getId());
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60 * 24 * 7);
            response.addCookie(cookie);
        }
        return map;
    }

    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    @ResponseBody
    public Map logout(HttpServletRequest request,HttpServletResponse response){
        Map map = new HashMap();
        User user = cookieUtils.getUser(request);
        if (user == null){
            map.put("errorCode",ResponseStatusEnum.LogoutError.getStatusCode());
            map.put("errorInfo",ResponseStatusEnum.LogoutError.getStatusInfo());
            return map;
        }
        Cookie cookie = new Cookie("token","");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        tokenService.updateTokenState(user.getId(),0);
        map.put("errorCode",ResponseStatusEnum.Success.getStatusCode());
        map.put("errorInfo",ResponseStatusEnum.Success.getStatusInfo());
        return map;
    }

}
