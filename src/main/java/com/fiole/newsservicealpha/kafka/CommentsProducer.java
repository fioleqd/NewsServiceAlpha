package com.fiole.newsservicealpha.kafka;

import com.alibaba.fastjson.JSON;
import com.fiole.newsservicealpha.entity.Comment;
import com.fiole.newsservicealpha.model.SubmitCommentRequestModel;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class CommentsProducer {
    @Resource
    private KafkaTemplate<String,String> kafkaTemplate;
    @Value("${kafka.topic.comment}")
    private String topic;
    public void send(Comment comment){
        String string = JSON.toJSONString(comment);
        ProducerRecord<String,String> record = new ProducerRecord<>(topic,string);
        kafkaTemplate.send(record);
    }
}
