package com.fraudfreeswarajya.sindhudurg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fraudfreeswarajya.sindhudurg.dto.TransactionResponse;
import com.fraudfreeswarajya.sindhudurg.model.RiskIndicator;
import com.fraudfreeswarajya.sindhudurg.model.RiskLevel;
import com.fraudfreeswarajya.sindhudurg.service.FraudScoringService;
import com.fraudfreeswarajya.sindhudurg.tanaji.dto.FraudScoreRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(FraudScoringController.class)
public class FraudScoringControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private FraudScoringService fraudScoringService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

   

    @Test
    public void testProcessTransaction_returnsTransactionResponse() throws Exception {
        // Given
        FraudScoreRequest request = new FraudScoreRequest();
        request.setTransaction_id("txn001");
        request.setAmount(new BigDecimal("10000.00"));
        request.setLocation("abc");

        TransactionResponse mockResponse = TransactionResponse.builder()
                .transactionId("txn001")
                .fraudScore(0.95)
                .riskLevel(RiskLevel.HIGH)
                .explanation(List.of("High amount", "High-risk country"))
                .riskIndicators(List.of(RiskIndicator.HIGH_AMOUNT))
                .recommendation("REJECT")

                .build();

        Mockito.when(fraudScoringService.processTransaction(Mockito.any()))
                .thenReturn(mockResponse);

        // When & Then
        mockMvc.perform(post("/score")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(mockResponse)));
    }
}
