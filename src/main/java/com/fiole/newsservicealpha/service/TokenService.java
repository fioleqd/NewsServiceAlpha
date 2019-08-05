package com.fiole.newsservicealpha.service;

import com.fiole.newsservicealpha.entity.Token;
import com.fiole.newsservicealpha.dao.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TokenService {
    @Autowired
    TokenRepository tokenRepository;

    public Token getToken(int userId,String token){
        return tokenRepository.getFirstByUserIdAndTokenAndState(userId,token,1);
    }

    @Transactional
    public void updateTokenState(int userId,int state){
        tokenRepository.updateTokenState(userId,state);
    }

    public Token saveToken(Token token){
        return tokenRepository.save(token);
    }
}
