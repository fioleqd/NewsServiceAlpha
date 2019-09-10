package com.fiole.newsservicealpha.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class BrowseProducer {
    @Resource
    private KafkaTemplate<String,String> kafkaTemplate;
    @Value("${kafka.topic.browse}")
    private String topic;
    public void send(String value){
        ProducerRecord<String,String> record = new ProducerRecord<>(topic,"articleId",value);
        kafkaTemplate.send(record);
    }
}
