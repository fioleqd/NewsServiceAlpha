package com.fiole.newsservicealpha.service;

import com.fiole.newsservicealpha.entity.User;
import com.fiole.newsservicealpha.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User getUserByUsernameAndPassword(String username, String password){
        return userRepository.getFirstByUsernameAndPasswordAndState(username,password,1);
    }

    public User getUserById(int id){
        return userRepository.getOne(id);
    }

    public User getUserByUsername(String username){
        return userRepository.getFirstByUsernameAndState(username,1);
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }
}
