package com.github.taven.websocket;

import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.io.IOException;

@Component
public class WebSocketSupport {

    public void send(String sessionId, String message) {
        Session session = WsSessionManager.getSession(sessionId);

        if (session != null) {
            this.send(session, message);

        } else {
            // 无法发送的情况 TODO

        }


    }

    public void send(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
