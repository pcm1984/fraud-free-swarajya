package com.fraudfreeswarajya.sindhudurg.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {

    @NotBlank
    @JsonProperty("transaction_id")
    private String transactionId;

    @NotBlank
    @JsonProperty("user_id")
    private String userId;

    @NotNull
    @DecimalMin("0.01")
    @JsonProperty("amount")
    private BigDecimal amount;

    @NotBlank
    @JsonProperty("currency")
    private String currency;

    @NotBlank
    @JsonProperty("payment_method")
    private String paymentMethod;

    @NotBlank
    @JsonProperty("card_number_last4")
    private String cardNumberLast4;

    @NotNull
    @JsonProperty("transaction_time")
    private Instant transactionTime;

    @NotBlank
    @JsonProperty("location")
    private String location;

    @NotBlank
    @JsonProperty("device_id")
    private String deviceId;

    @NotBlank
    @JsonProperty("ip_address")
    private String ipAddress;

    @NotBlank
    @JsonProperty("merchant_id")
    private String merchantId;
}