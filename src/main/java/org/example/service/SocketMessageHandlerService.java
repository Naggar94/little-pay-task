package org.example.service;

import java.security.InvalidParameterException;

import org.example.message.decoder.Base64Decoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SocketMessageHandlerService {
    private final Base64Decoder base64Decoder;

    public String handleMessage(String inputLine) {
        return new String(base64Decoder.decode(inputLine));
    }
}
