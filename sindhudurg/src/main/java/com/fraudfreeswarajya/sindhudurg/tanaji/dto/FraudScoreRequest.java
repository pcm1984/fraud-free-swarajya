package com.fraudfreeswarajya.sindhudurg.tanaji.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FraudScoreRequest {
    private String transaction_id;
    private String user_id;
    private BigDecimal amount;
    private String currency;
    private String location;
    private String ip_address;
    private String merchant_id;
    private String timestamp;
}
