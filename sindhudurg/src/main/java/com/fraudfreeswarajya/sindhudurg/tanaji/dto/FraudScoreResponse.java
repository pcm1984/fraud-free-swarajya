package com.fraudfreeswarajya.sindhudurg.tanaji.dto;

import com.fraudfreeswarajya.sindhudurg.model.RiskIndicator;
import lombok.Data;

import java.util.List;

@Data
public class FraudScoreResponse {
    private String transaction_id;
    private double fraud_score;
    private List<String> explanation;
    private List<RiskIndicator> riskIndicators;
}
