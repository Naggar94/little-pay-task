package org.example;

import org.example.pojo.TransactionDataRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TransactionControllerTest extends BaseTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void testSubmitTransaction() throws Exception {
        TransactionDataRequest request = TransactionDataRequest.builder()
                .data("AEICGJ8qAQKfAgIBAFoIQRERERERERFfKgIJeAMCJJ8qCAQAAAAAAAAAXyoCCCafAgMSNFZaCDeCgiRjEABfnwMBAAM=")
                .build();
        mockMvc.perform(post("/transaction/submit")
                .header("Content-type", "application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].kernel").value("Amex"))
                .andExpect(jsonPath("$[1].kernel").value("Mastercard"));
    }
}
