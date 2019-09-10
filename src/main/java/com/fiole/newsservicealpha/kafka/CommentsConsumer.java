package com.fiole.newsservicealpha.kafka;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fiole.newsservicealpha.entity.Comment;
import com.fiole.newsservicealpha.model.SubmitCommentRequestModel;
import com.fiole.newsservicealpha.service.ArticleService;
import com.fiole.newsservicealpha.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class CommentsConsumer {
    @Autowired
    private ArticleService articleService;
    @Value("${kafka.topic.comment}")
    private String topic;
    @KafkaListener(topics = {"${kafka.topic.comment}"},groupId = "test")
    public void registryReceiver(ConsumerRecord<String, String> record) {
        String kafkaValue = record.value();
        log.info("consume message from topic {},value is {}",topic,kafkaValue);
        Comment comment = JSON.parseObject(kafkaValue,Comment.class);
        articleService.doComment(comment.getItemId(), comment);
    }
}
