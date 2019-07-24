package com.fiole.newsservicealpha.repository;

import com.fiole.newsservicealpha.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
}
