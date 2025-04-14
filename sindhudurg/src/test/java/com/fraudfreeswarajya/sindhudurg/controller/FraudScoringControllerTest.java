package com.fraudfreeswarajya.sindhudurg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fraudfreeswarajya.sindhudurg.dto.TransactionRequest;
import com.fraudfreeswarajya.sindhudurg.service.FraudScoringService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.Instant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@WebMvcTest(FraudScoringController.class)
@SpringBootTest
@AutoConfigureMockMvc
class FraudScoringControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private FraudScoringService scoringService;


    private TransactionRequest createRequest() {
        TransactionRequest request = new TransactionRequest();
        request.setTransactionId("txn-test");
        request.setUserId("user-001");
        request.setAmount(new BigDecimal("100.00"));
        request.setCurrency("INR");
        request.setPaymentMethod("credit_card");
        request.setCardNumberLast4("1234");
        request.setTransactionTime(Instant.now());
        request.setLocation("Pune");
        request.setIpAddress("127.0.0.1");
        request.setDeviceId("dev-01");
        request.setMerchantId("merchant-001");
        return request;
    }

    @Test
    void shouldReturnHighRiskWhenScoreIsAbove80() throws Exception {
        // Mock the score
        Mockito.when(scoringService.calculateScore()).thenReturn(0.85);

        final MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(
                "/api/v1/fraud-score"
        ).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createRequest()));

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.riskLevel").value("HIGH"))
                .andExpect(jsonPath("$.recommendation").value("REJECT"));
    }

    @Test
    void shouldReturnMediumRiskWhenScoreIsBetween40And80() throws Exception {
        Mockito.when(scoringService.calculateScore()).thenReturn(0.60);

        mockMvc.perform(post("/api/v1/fraud-score")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.riskLevel").value("MEDIUM"))
                .andExpect(jsonPath("$.recommendation").value("REVIEW"));
    }

    @Test
    void shouldReturnLowRiskWhenScoreIsBelow40() throws Exception {
        Mockito.when(scoringService.calculateScore()).thenReturn(0.20);

        mockMvc.perform(post("/api/v1/fraud-score")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.riskLevel").value("LOW"))
                .andExpect(jsonPath("$.recommendation").value("APPROVE"));
    }

    //Negative tests

    @Test
    void shouldFailWhenAmountIsMissing() throws Exception {
        String invalidJson = """
        {
          "transactionId": "txn_001",
          "userId": "user_123",
          "currency": "INR"
        }
        """;

        mockMvc.perform(post("/api/v1/fraud-score")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldFailForInvalidJson() throws Exception {
        String malformedJson = """
        {
          "transactionId": "txn_001",
          "userId": "user_123",
          "amount": 100.0,
          "currency": "INR"
        """; // Missing closing brace

        mockMvc.perform(post("/api/v1/fraud-score")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(malformedJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldFailWhenBodyIsEmpty() throws Exception {
        mockMvc.perform(post("/api/v1/fraud-score")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());
    }


}
