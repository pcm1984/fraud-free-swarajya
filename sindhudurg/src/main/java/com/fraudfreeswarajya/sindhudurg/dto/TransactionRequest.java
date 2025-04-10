package com.fraudfreeswarajya.sindhudurg.dto;

import lombok.Data;
import java.time.Instant;
import java.math.BigDecimal;

@Data
public class TransactionRequest {
    private String transactionId;
    private String userId;
    private BigDecimal amount;
    private String currency;
    private String paymentMethod;
    private String cardNumberLast4;
    private Instant transactionTime;
    private String location;
    private String ipAddress;
    private String deviceId;
    private String merchantId;
}

