package com.fraudfreeswarajya.sindhudurg.tanaji.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fraudfreeswarajya.sindhudurg.model.RiskIndicator;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FraudScoreResponse {

    @JsonProperty("transaction_id")
    private String transactionId;

    @JsonProperty("fraud_score")
    private double fraudScore;

    private List<String> explanation;

    @JsonProperty("risk_indicators")
    private List<RiskIndicator> riskIndicators;

    private String riskLevel;
    private String recommendation;
}
