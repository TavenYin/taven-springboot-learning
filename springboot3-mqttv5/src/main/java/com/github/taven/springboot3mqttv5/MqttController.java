package com.github.taven.springboot3mqttv5;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MqttController {

    @Autowired
    @Qualifier("mqttOutFlow")
    private IntegrationFlow mqttOutFlow;

    @GetMapping("/sendMsg")
    public void sendMsg() {
        Message<String> msg = MessageBuilder.withPayload("hello, springboot 来的")
                .setHeader(MqttHeaders.TOPIC, "testtopic/5")
                .build();
        mqttOutFlow.getInputChannel().send(msg);
    }

}
