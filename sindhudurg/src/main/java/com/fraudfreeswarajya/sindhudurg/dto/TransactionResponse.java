package com.fraudfreeswarajya.sindhudurg.dto;

import com.fraudfreeswarajya.sindhudurg.model.RiskLevel;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TransactionResponse {
    private String transactionId;
    private double fraudScore;
    private RiskLevel riskLevel;
    private List<String> explanation;
    private String recommendation;
}

