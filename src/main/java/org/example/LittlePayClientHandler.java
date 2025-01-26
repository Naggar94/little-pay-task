package org.example;

import org.example.message.decoder.Base64Decoder;
import org.example.message.handler.MultipleMessageParser;
import org.example.service.SocketMessageHandlerService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class LittlePayClientHandler {
//    private final MultipleMessageParser multipleMessageParser = new MultipleMessageParser();
//    private final SocketMessageHandlerService socketMessageHandlerService = new SocketMessageHandlerService();
//
//    public void run(Socket clientSocket) throws IOException {
//        try(BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
//            String inputLine;
//            while ((inputLine = in.readLine()) != null) {
//                System.out.println(inputLine);
////                multipleMessageParser.parse(socketMessageHandlerService.handleMessage(inputLine.trim()));
//            }
//        }
//    }
}
