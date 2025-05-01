package com.fraudfreeswarajya.sindhudurg.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fraudfreeswarajya.sindhudurg.model.RiskIndicator;
import com.fraudfreeswarajya.sindhudurg.model.RiskLevel;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TransactionResponse {
    private String transactionId;
    private double fraudScore;
    private RiskLevel riskLevel;
    private List<String> explanation;
    @JsonProperty("risk_indicators")
    private List<RiskIndicator> riskIndicators;
    private String recommendation;
}

