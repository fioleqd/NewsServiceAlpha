package com.fiole.newsservicealpha.controller;

import com.fiole.newsservicealpha.Enum.ResponseStatusEnum;
import com.fiole.newsservicealpha.entity.User;
import com.fiole.newsservicealpha.model.RegistryRequestModel;
import com.fiole.newsservicealpha.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping
@Slf4j
public class RegistryController {

    @Autowired
    UserService userService;

    @RequestMapping("/registry")
    public String registry(){
        return "registry";
    }

    @RequestMapping(value = "/registry/ajax",method = RequestMethod.POST)
    @ResponseBody
    public Map submitRegistry(@RequestBody @NotNull RegistryRequestModel requestModel){
        Map<String,Object> map = new HashMap<>();
        User user = userService.getUserByUsername(requestModel.getUsername());
        if (user != null){
            log.warn("User has exist, username is {}",requestModel.getUsername());
            map.put("errorCode", ResponseStatusEnum.UserHasExist.getStatusCode());
            map.put("errorInfo",ResponseStatusEnum.UserHasExist.getStatusInfo());
            return map;
        }
        if (!StringUtils.equals(requestModel.getRePassword(),requestModel.getPassword())){
            log.warn("Password input twice not same");
            map.put("errorCode", ResponseStatusEnum.RegistryPasswordNotSame.getStatusCode());
            map.put("errorInfo",ResponseStatusEnum.RegistryPasswordNotSame.getStatusInfo());
            return map;
        }
        String usernameReg = "^[0-9a-zA-Z_]{8,16}$";
        if (!requestModel.getUsername().matches(usernameReg)){
            log.warn("Invalid username");
            map.put("errorCode", ResponseStatusEnum.InvalidUsername.getStatusCode());
            map.put("errorInfo", ResponseStatusEnum.InvalidUsername.getStatusInfo());
            return map;
        }
        String pwLengthReg = ".{8,16}";
        if (!requestModel.getPassword().matches(pwLengthReg)){
            log.warn("Wrong password length");
            map.put("errorCode", ResponseStatusEnum.WrongPasswordLength.getStatusCode());
            map.put("errorLength", ResponseStatusEnum.WrongPasswordLength.getStatusInfo());
            return map;
        }

        String pwReg = "^(?![a-zA-z]+$)(?!\\d+$)(?![!@#$%^&*]+$)[a-zA-Z\\d!@#$%^&*]+$";
        if (!requestModel.getPassword().matches(pwReg)){
            log.warn("Invalid password");
            map.put("errorCode", ResponseStatusEnum.InvalidPassword.getStatusCode());
            map.put("error", ResponseStatusEnum.InvalidPassword.getStatusInfo());
            return map;
        }

        user = new User();
        user.setNickname(requestModel.getNickname());
        user.setAvatar("/image/avatar.png");
        user.setPassword(requestModel.getPassword());
        user.setUsername(requestModel.getUsername());
        user.setState(1);
        Date now = new Date();
        user.setCreateTime(now);
        user.setUpdateTime(now);
        userService.saveUser(user);
        map.put("errorCode", ResponseStatusEnum.Success.getStatusCode());
        map.put("errorInfo", ResponseStatusEnum.Success.getStatusInfo());
        return map;
    }
}
