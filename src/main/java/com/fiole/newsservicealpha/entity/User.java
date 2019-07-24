package com.fiole.newsservicealpha.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name = "tb_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    private String nickname;
    private String avatar;
    private int state;
    private Date createTime;
    private Date updateTime;
}
