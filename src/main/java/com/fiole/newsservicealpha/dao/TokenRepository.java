package com.fiole.newsservicealpha.dao;

import com.fiole.newsservicealpha.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token,Integer> {
    Token getFirstByUserIdAndTokenAndState(int userId,String token,int state);

    @Modifying
    @Query("update Token t set t.state = ?2 where t.userId = ?1")
    void updateTokenState(int userId,int state);
}
