package org.example.handler;

import org.example.message.decoder.Base64Decoder;
import org.example.message.handler.MultipleMessageParser;
import org.example.service.SocketMessageHandlerService;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LittlePayClientHandler {
    private final MultipleMessageParser multipleMessageParser;
    private final SocketMessageHandlerService socketMessageHandlerService;

    public void run(Socket clientSocket) throws IOException {
        try(BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
                multipleMessageParser.parse(socketMessageHandlerService.handleMessage(inputLine.trim()));
            }
        }
    }
}
