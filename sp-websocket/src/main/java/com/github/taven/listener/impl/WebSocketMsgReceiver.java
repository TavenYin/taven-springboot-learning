package com.github.taven.listener.impl;

import com.github.taven.listener.RedisChannel;
import com.github.taven.listener.RedisMsgReceiver;
import com.github.taven.sender.MsgDTO;
import com.github.taven.websocket.WebSocketSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class WebSocketMsgReceiver implements RedisMsgReceiver {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String getChannelName() {
        return RedisChannel.WS_SEND_MSG;
    }

    @Override
    public void receiveMsg(String message) {
        MsgDTO msg = (MsgDTO) redisTemplate.getValueSerializer().deserialize(message.getBytes());
        WebSocketSupport.tryPush(msg);
    }
}
