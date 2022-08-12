package com.github.taven.springkafka.consumer;

import com.github.taven.springkafka.model.MessageWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.FixedDelayStrategy;
import org.springframework.retry.annotation.Backoff;

/**
 * @author tianwen.yin
 */
@Slf4j
public class KafkaConsumer {
    @RetryableTopic(
            kafkaTemplate = "kafkaTemplate",
            attempts = "${kafka.retry.attempts}",
            backoff = @Backoff(delayExpression = "${kafka.retry.delay}", multiplierExpression = "${kafka.retry.multiplier}")
//            ,fixedDelayTopicStrategy = FixedDelayStrategy.SINGLE_TOPIC
    )
    @KafkaListener(topics = "test_topic", groupId = "demo01-consumer-group-1")
    public void onMessage(MessageWrapper message) {
        log.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
        throw new RuntimeException("test kafka exception");
    }

    @DltHandler
    public void processMessage(MessageWrapper message) {
        log.info("dlt {}", message);
    }

}
