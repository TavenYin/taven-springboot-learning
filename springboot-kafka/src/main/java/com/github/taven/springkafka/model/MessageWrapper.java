package com.github.taven.springkafka.model;

import lombok.Data;

/**
 * @author tianwen.yin
 */
@Data
public class MessageWrapper {

    private String id;

    private String body;

    public MessageWrapper() {
    }

    public MessageWrapper(String id, String body) {
        this.id = id;
        this.body = body;
    }
}
