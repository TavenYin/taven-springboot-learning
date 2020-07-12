package com.github.taven.websocket;

import com.github.taven.sender.MsgDTO;
import org.springframework.util.StringUtils;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.github.taven.websocket.util.*;

public class WebSocketSupport {

    private static final WsSessionManager sessionManager = new WsSessionManager();

    private WebSocketSupport() {}

    /**
     * 尝试向客户端推送消息
     *
     * @param msgDTO
     */
    public static void tryPush(MsgDTO msgDTO) {
        if (msgDTO == null)
            return;

        String userId = String.valueOf(msgDTO.getReceiverId());
        Session session = sessionManager.get(userId);

        if (session != null && session.isOpen()) {
            push(session, msgDTO.getMsgBody());
        }

    }

    public static void push(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void storageSession(Session session) {
        String querystring = session.getQueryString();

        if (!StringUtils.isEmpty(querystring)) {
            Map<String, String> param = QueryStringUtil.parse(querystring);
            String key = param.get("userId");

            sessionManager.save(key ,session);
        }
    }

    public static Session getSession(String id) {
        return sessionManager.get(id);
    }

    public static void releaseSession(String id) {
        sessionManager.releaseSession(id);
    }

    public static void releaseSession(Session session) {
        String querystring = session.getQueryString();

        if (!StringUtils.isEmpty(querystring)) {
            Map<String, String> param = QueryStringUtil.parse(querystring);
            String key = param.get("userId");

            sessionManager.releaseSession(key);
        }
    }

    private static class WsSessionManager {

        final ConcurrentHashMap<Object, Session> sessionPool = new ConcurrentHashMap<>();

        void save(Object key, Session session) {

            sessionPool.put(key, session);
        }

        Session get(String key) {
            return sessionPool.get(key);
        }

        boolean haveSession(String key) {
            return sessionPool.containsKey(key);
        }

        void releaseSession(String key) {
            Session session = sessionPool.remove(key);
            try {
                if (session.isOpen())
                    session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}
