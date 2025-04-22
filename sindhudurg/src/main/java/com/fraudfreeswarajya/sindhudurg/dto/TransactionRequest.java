package com.fraudfreeswarajya.sindhudurg.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionRequest {

    @NotNull
    private String transactionId;

    @NotNull
    private String userId;

    @NotNull
    @DecimalMin("0.01")
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

