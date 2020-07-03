package com.github.taven.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.util.List;

/**
 * redis敏感词消息订阅
 *
 * @author fk
 * @version 2.0
 * @since 7.1.5
 * 2019-09-07 18：00
 */
@Configuration
public class RedisListener {

    @Autowired(required = false)
    private List<RedisMsgReceiver> receiverList;

    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        if (receiverList != null) {
            for (RedisMsgReceiver receiver : receiverList) {
                container.addMessageListener(listenerAdapter(receiver), new PatternTopic(receiver.getChannelName()));
            }
        }

        return container;
    }


    /**
     * 绑定消息监听者和接收监听的方法
     */
    private MessageListenerAdapter listenerAdapter(RedisMsgReceiver receiver) {
        MessageListenerAdapter listenerAdapter = new MessageListenerAdapter(receiver, "receiveMsg");
        listenerAdapter.afterPropertiesSet();
        return listenerAdapter;
    }

}
