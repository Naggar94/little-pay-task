package org.example.controller;

import java.util.List;

import org.example.message.handler.MultipleMessageParser;
import org.example.pojo.TransactionDataRequest;
import org.example.pojo.TransactionDataResponse;
import org.example.service.SocketMessageHandlerService;
import org.example.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/submit")
    public ResponseEntity<List<TransactionDataResponse>> validateInput(@RequestBody @Validated TransactionDataRequest request) {
        return ResponseEntity.ok(transactionService.handle(request.getData()));
    }
}
