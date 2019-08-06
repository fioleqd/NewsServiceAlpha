package com.fiole.newsservicealpha.service;

import com.fiole.newsservicealpha.entity.Token;
import com.fiole.newsservicealpha.dao.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class TokenService {
    @Autowired
    TokenRepository tokenRepository;

    public Token getToken(int userId,String token){
        return tokenRepository.getToken(userId,token,new Date());
    }

    @Transactional
    public void updateTokenState(int userId,int state){
        tokenRepository.updateTokenState(userId,state);
    }

    public Token saveToken(Token token){
        return tokenRepository.save(token);
    }
}
