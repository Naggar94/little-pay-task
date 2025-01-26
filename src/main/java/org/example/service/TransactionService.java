package org.example.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.example.entity.MessageEntity;
import org.example.mapper.MessageMapper;
import org.example.message.handler.MultipleMessageParser;
import org.example.pojo.TransactionDataResponse;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final MultipleMessageParser multipleMessageParser;
    private final SocketMessageHandlerService socketMessageHandlerService;
    private final MessageMapper messageMapper;

    public List<TransactionDataResponse> handle(String payload) {
        Map<String, List<MessageEntity>> response = multipleMessageParser.parse(socketMessageHandlerService.handleMessage(payload));
        return response.keySet().stream()
                .map(response::get)
                .flatMap(Collection::stream)
                .map(messageMapper::messageEntityToTransactionDataResponse)
                .collect(Collectors.toList());
    }
}
