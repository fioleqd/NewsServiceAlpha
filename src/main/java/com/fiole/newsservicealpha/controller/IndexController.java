package com.fiole.newsservicealpha.controller;

import com.fiole.newsservicealpha.entity.User;
import com.fiole.newsservicealpha.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.Date;

@Controller
@RequestMapping("/index")
public class IndexController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(path = "/test",method = RequestMethod.GET)
    public String getIndexByType(Model model){
        model.addAttribute("name","test");
        return "index";
    }
}
