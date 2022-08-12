package com.github.taven.springkafka;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConsumerRecordRecoverer;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.ErrorHandler;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.kafka.retrytopic.RetryTopicConfiguration;
import org.springframework.kafka.retrytopic.RetryTopicConfigurationBuilder;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.FixedBackOff;

/**
 * @author tianwen.yin
 */
@Configuration
public class KafkaConfiguration {

    /**
     * 该异常处理，仅在未使用 Retry Topic 的情况下生效
     */
    @Bean
    public ErrorHandler errorHandler(KafkaTemplate<Object, Object> kafkaTemplate) {
        ConsumerRecordRecoverer recoverer = new DeadLetterPublishingRecoverer(kafkaTemplate);
        // 设置重试间隔 10秒 次数为 3次
        BackOff backOff = new FixedBackOff(10 * 1000L, 3L);
        // 创建 SeekToCurrentErrorHandler 对象
        return new SeekToCurrentErrorHandler(recoverer, backOff);
    }

    /**
     * 全局 Retry Topic 配置
     */
    @Bean
    public RetryTopicConfiguration myRetryTopic(KafkaTemplate<String, Object> template) {
        return RetryTopicConfigurationBuilder
                .newInstance()
                .maxAttempts(4)
                .fixedBackOff(15000)
                .includeTopic("test_topic2")
                .create(template);
    }

}
