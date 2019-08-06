package com.fiole.newsservicealpha.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RequestException extends RuntimeException{
    private String description;

    public RequestException(){

    }

    public RequestException(String description){
        this.description = description;
    }
}
