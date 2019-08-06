package com.fiole.newsservicealpha.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "tb_token")
public class Token implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int userId;
    private String token;
    private String userInfo;
    private Date invalidTime;
    private int state;
    private Date createTime;
    private Date updateTime;
}
