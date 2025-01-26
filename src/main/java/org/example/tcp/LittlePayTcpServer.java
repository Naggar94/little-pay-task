package org.example.tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.example.handler.LittlePayClientHandler;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LittlePayTcpServer implements Runnable {
    private final int port = 8888;
    private final LittlePayClientHandler littlePayClientHandler;

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new ClientHandler(clientSocket, littlePayClientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AllArgsConstructor
    static class ClientHandler extends Thread {
        private final Socket clientSocket;
        private LittlePayClientHandler littlePayClientHandler;
        @Override
        public void run() {
            try {
                littlePayClientHandler.run(clientSocket);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}