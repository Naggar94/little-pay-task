package org.example.configuration;

import org.example.LittlePaySocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.websocket.server.ServerEndpointConfig;

@Component
public class LittlePayWebSocketServerConfig extends ServerEndpointConfig.Configurator {
    private static LittlePaySocketServer littlePaySocketServer;

    @Autowired
    public void setLittlePaySocketServer(LittlePaySocketServer littlePaySocketServer) {
        LittlePayWebSocketServerConfig.littlePaySocketServer = littlePaySocketServer;
    }

    @Override
    public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
        if (LittlePaySocketServer.class.equals(endpointClass)) {
            return (T) littlePaySocketServer;
        }
        return super.getEndpointInstance(endpointClass);
    }
}
