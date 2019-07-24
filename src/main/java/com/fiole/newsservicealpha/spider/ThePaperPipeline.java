package com.fiole.newsservicealpha.spider;

import com.fiole.newsservicealpha.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ThePaperPipeline {
    @Autowired
    ArticleRepository articleRepository;
}
