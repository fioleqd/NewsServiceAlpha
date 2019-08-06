package com.fiole.newsservicealpha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class NewsservicealphaApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(NewsservicealphaApplication.class, args);
    }

    //打包SpringBoot项目
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }
}
