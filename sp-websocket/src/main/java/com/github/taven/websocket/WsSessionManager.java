package com.github.taven.websocket;

import javax.websocket.Session;
import java.util.concurrent.ConcurrentHashMap;

public class WsSessionManager {

    private static final ConcurrentHashMap<String, Session> sessionPool = new ConcurrentHashMap<>();

    public static void saveSession(Session session) {
        sessionPool.put(session.getId(), session);
    }

    public static Session getSession(String sessionId) {
        return sessionPool.get(sessionId);
    }

}
