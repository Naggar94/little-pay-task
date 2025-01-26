package org.example.tcp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;


@Component
public class TcpServerStarter {

    private final LittlePayTcpServer littlePayTcpServer;

    @Autowired
    public TcpServerStarter(LittlePayTcpServer littlePayTcpServer) {
        this.littlePayTcpServer = littlePayTcpServer;
    }

    @PostConstruct
    public void startTcpServer() {
        System.out.println("TCP Server Started at port: 8888");
        new Thread(littlePayTcpServer).start();
    }
}
