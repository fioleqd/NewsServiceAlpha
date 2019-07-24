package com.fiole.newsservicealpha.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name = "tb_response")
public class Response {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int itemId;
    private String content;
    private int parentId;
    private String path;
    private int state;
    private Date createTime;
    private Date updateTime;
}
