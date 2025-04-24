package com.fraudfreeswarajya.sindhudurg.service;

import com.fraudfreeswarajya.sindhudurg.dto.TransactionRequest;
import com.fraudfreeswarajya.sindhudurg.dto.TransactionResponse;
import com.fraudfreeswarajya.sindhudurg.ramchandrapant.FraudTransactionEntity;
import com.fraudfreeswarajya.sindhudurg.ramchandrapant.FraudTransactionRepository;
import com.fraudfreeswarajya.sindhudurg.tanaji.FraudScoringClient;
import com.fraudfreeswarajya.sindhudurg.tanaji.dto.FraudScoreResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.Instant;

@Service
public class FraudScoringService {

    private static final Logger log = LoggerFactory.getLogger(FraudScoringService.class);

    @Autowired
    private FraudScoringClient fraudScoringClient;

    @Autowired
    private FraudTransactionRepository fraudTransactionRepository;


    public TransactionResponse processTransaction(TransactionRequest request) {


        TransactionResponse response = fraudScoringClient.getFraudScore(request);

        // Save to Postgres
        FraudTransactionEntity entity = FraudTransactionEntity.builder()
                .transactionId(request.getTransactionId())
                .userId(request.getUserId())
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .location(request.getLocation())
                .ipAddress(request.getIpAddress())
                .merchantId(request.getMerchantId())
                .timestamp(request.getTransactionTime())
                .fraudScore(BigDecimal.valueOf(response.getFraudScore()))
                .riskLevel(response.getRiskLevel())
                .recommendation(response.getRecommendation())
                .createdAt(Instant.now())
                .build();

        fraudTransactionRepository.save(entity);

        log.info("Fraud score for txn {}: {} - Explanation: {}",
                response.getTransactionId(),
                response.getFraudScore(),
                response.getExplanation());

        return response;
    }
}
