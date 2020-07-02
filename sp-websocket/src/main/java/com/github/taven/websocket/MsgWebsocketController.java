package com.github.taven.websocket;

import org.springframework.stereotype.Controller;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint(value = "/message_websocket")
@Controller
public class MsgWebsocketController {

    @OnOpen
    public void onOpen(Session session) {
        WsSessionManager.saveSession(session);
        System.out.println("session open. ID:" + session.getId());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        System.out.println("session close. ID:" + session.getId());
    }

    /**
     * 收到客户端消息后调用的方法
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        System.out.println("get client msg. ID:" + session.getId() + ". msg:" + message);
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        // 啥时候走这个?
        System.out.println("发生错误》》发生时间：" + System.currentTimeMillis() + "  ID是：" + session.getId());

        error.printStackTrace();
    }

}
