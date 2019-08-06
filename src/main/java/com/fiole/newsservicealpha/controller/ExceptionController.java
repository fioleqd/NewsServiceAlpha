package com.fiole.newsservicealpha.controller;

import com.fiole.newsservicealpha.exception.RequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionController {
    @ExceptionHandler(RequestException.class)
    public String showError(){
        return "500";
    }
}
