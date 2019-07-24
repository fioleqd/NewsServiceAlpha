package com.fiole.newsservicealpha.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name = "tb_article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String content;
    private String source;
    private Date time;
    private int responseNumber;
    private int browseNumber;
    private String type;
    private int state;
    private Date createTime;
    private Date updateTime;
}
