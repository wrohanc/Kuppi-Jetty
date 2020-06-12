package com.ro.learn;

import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/echo")
public class WSDemo {

    @OnMessage
    public void onMessage(Session session, String message) {
        session.getAsyncRemote().sendText("Response from server : " + message);
    }
}
