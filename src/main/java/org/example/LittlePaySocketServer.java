package org.example;

import org.example.configuration.LittlePayWebSocketServerConfig;
import org.example.message.handler.MultipleMessageParser;
import org.example.service.SocketMessageHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

@Component
@ServerEndpoint(value = "/websocket", configurator = LittlePayWebSocketServerConfig.class)
public class LittlePaySocketServer {
    @Autowired
    private MultipleMessageParser multipleMessageParser;
    @Autowired
    private SocketMessageHandlerService socketMessageHandlerService;
    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Connection opened: " + session.getId());
        try {
            session.getBasicRemote().sendText("Connection established.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("Message received: " + message);
        try {
            multipleMessageParser.parse(socketMessageHandlerService.handleMessage(message.trim()));
            session.getBasicRemote().sendText("Server received: " + message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("Connection closed: " + session.getId());
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.err.println("Error on session " + session.getId() + ": " + throwable.getMessage());
    }
}
