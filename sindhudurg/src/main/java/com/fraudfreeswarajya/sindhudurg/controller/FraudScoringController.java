package com.fraudfreeswarajya.sindhudurg.controller;

import com.fraudfreeswarajya.sindhudurg.dto.TransactionRequest;
import com.fraudfreeswarajya.sindhudurg.dto.TransactionResponse;
import org.springframework.web.bind.annotation.*;
import com.fraudfreeswarajya.sindhudurg.model.RiskLevel;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class FraudScoringController {

    @PostMapping("/fraud-score")
    public TransactionResponse scoreTransaction(@RequestBody TransactionRequest request) {
        double dummyScore = Math.random(); // Simulated score
        RiskLevel riskLevel = getRiskLevel(dummyScore);

        return TransactionResponse.builder()
                .transactionId(request.getTransactionId())
                .fraudScore(dummyScore)
                .riskLevel(riskLevel)
                .explanation(List.of("Simulated AI output"))
                .recommendation(getRecommendation(riskLevel))
                .build();
    }

    private RiskLevel getRiskLevel(double score) {
        if (score >= 0.8) return RiskLevel.HIGH;
        if (score >= 0.4) return RiskLevel.MEDIUM;
        return RiskLevel.LOW;
    }

    private String getRecommendation(RiskLevel level) {
        return switch (level) {
            case HIGH -> "REJECT";
            case MEDIUM -> "REVIEW";
            case LOW -> "APPROVE";
        };
    }
}
