package com.github.taven.springkafka.consumer;

import com.github.taven.springkafka.model.MessageWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;

/**
 * @author tianwen.yin
 */
@Slf4j
public class SimpleConsumer {

//    @RetryableTopic()
    @KafkaListener(topics = "test_topic2", groupId = "demo01-consumer-group-1")
    public void onMessage(MessageWrapper message, ConsumerRecord record) {
        log.info("[onMessage][线程编号:{} Topic: {} 消息内容：{}]", Thread.currentThread().getId(), record.topic(), message);
        throw new RuntimeException("test kafka exception");
    }

}