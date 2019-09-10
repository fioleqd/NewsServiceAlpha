package com.fiole.newsservicealpha.kafka;

import com.fiole.newsservicealpha.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BrowseConsumer {
    @Autowired
    private ArticleService articleService;
    @Value("${kafka.topic.browse}")
    private String topic;
    @KafkaListener(topics = {"${kafka.topic.browse}"},groupId = "test")
    public void registryReceiver(ConsumerRecord<String, String> integerStringConsumerRecords) {
        String articleId = integerStringConsumerRecords.value();
        log.info("consume message from topic {},value is {}",topic,articleId);
        articleService.updateBrowseNumber(Integer.parseInt(articleId));
    }
}
