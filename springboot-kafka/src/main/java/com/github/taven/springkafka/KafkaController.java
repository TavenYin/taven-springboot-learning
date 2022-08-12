package com.github.taven.springkafka;

import com.github.taven.springkafka.model.MessageWrapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

/**
 * @author tianwen.yin
 */
@RestController
@RequestMapping("kafka")
public class KafkaController {

    @Resource
    private KafkaTemplate<Object, Object> kafkaTemplate;

    @GetMapping("/send-time")
    public void sendTime() {
        MessageWrapper msg = new MessageWrapper( UUID.randomUUID().toString(), "now time is " + new Date() );
        kafkaTemplate.send("test_topic2", msg);
    }

}
