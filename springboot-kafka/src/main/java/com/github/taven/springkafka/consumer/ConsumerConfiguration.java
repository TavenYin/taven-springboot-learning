package com.github.taven.springkafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author tianwen.yin
 */
@Slf4j
@Configuration
public class ConsumerConfiguration {

    @ConditionalOnProperty("consumer.kafka.enable")
    @Bean
    public KafkaConsumer kafkaConsumer() {
        log.info("KafkaConsumer enable");
        return new KafkaConsumer();
    }

    @ConditionalOnProperty("consumer.simple.enable")
    @Bean
    public SimpleConsumer simpleConsumer() {
        log.info("SimpleConsumer enable");
        return new SimpleConsumer();
    }

}
