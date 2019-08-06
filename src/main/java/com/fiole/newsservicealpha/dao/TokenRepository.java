package com.fiole.newsservicealpha.dao;

import com.fiole.newsservicealpha.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface TokenRepository extends JpaRepository<Token,Integer> {
    @Query("select t from Token t where t.state = 1 and t.userId = ?1 and t.token = ?2 and t.invalidTime > ?3")
    Token getToken(int userId, String token, Date now);

    @Modifying
    @Query("update Token t set t.state = ?2 where t.userId = ?1")
    void updateTokenState(int userId,int state);
}
