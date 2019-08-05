package com.fiole.newsservicealpha.dao;

import com.fiole.newsservicealpha.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    User getFirstByUsernameAndPasswordAndState(String username,String password,int state);
    User getFirstByUsernameAndState(String username,int state);
}
