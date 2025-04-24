package com.fraudfreeswarajya.sindhudurg.ramchandrapant;

import com.fraudfreeswarajya.sindhudurg.model.RiskLevel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "fraud_transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FraudTransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String transactionId;
    private String userId;
    private BigDecimal amount;
    private String currency;
    private String location;
    private String ipAddress;
    private String merchantId;
    private Instant timestamp;

    private BigDecimal fraudScore;

    @Enumerated(EnumType.STRING)
    private RiskLevel riskLevel;

    private String recommendation;

    private Instant createdAt;
}
