package com.fraudfreeswarajya.sindhudurg.service;

import com.fraudfreeswarajya.sindhudurg.dadoji.RuleExecutor;
import com.fraudfreeswarajya.sindhudurg.dadoji.dto.RuleDecision;
import com.fraudfreeswarajya.sindhudurg.dto.TransactionRequest;
import com.fraudfreeswarajya.sindhudurg.dto.TransactionResponse;
import com.fraudfreeswarajya.sindhudurg.model.RiskLevel;
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
    private RuleExecutor ruleExecutor;

    @Autowired
    private FraudTransactionRepository fraudTransactionRepository;


    public TransactionResponse processTransaction(TransactionRequest request) {


        FraudScoreResponse aiResponse = fraudScoringClient.getFraudScore(request);

        log.info("Fraud score for txn {}: {} - Explanation: {}, RiskIndicators: {}",
                aiResponse.getTransactionId(),
                aiResponse.getFraudScore(),
                aiResponse.getExplanation(),
                aiResponse.getRiskIndicators());

        RuleDecision decision = ruleExecutor.evaluateAll(request, aiResponse.getFraudScore(), aiResponse.getRiskIndicators());

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
                .fraudScore(BigDecimal.valueOf(aiResponse.getFraudScore()))
                .riskLevel(RiskLevel.valueOf(aiResponse.getRiskLevel()))
                .recommendation(aiResponse.getRecommendation())
                .createdAt(Instant.now())
                .build();

        fraudTransactionRepository.save(entity);

        return mapToTransactionResponse(aiResponse);
    }

    private TransactionResponse mapToTransactionResponse(FraudScoreResponse res) {
        RiskLevel risk = getRiskLevel(res.getFraudScore());
        String recommendation = getRecommendation(res.getFraudScore());

        return TransactionResponse.builder()
                .transactionId(res.getTransactionId())
                .fraudScore(res.getFraudScore())
                .riskLevel(risk)
                .explanation(res.getExplanation())
                .recommendation(recommendation)
                .riskIndicators(res.getRiskIndicators())
                .build();
    }

    private RiskLevel getRiskLevel(double score) {
        if (score >= 0.8) return RiskLevel.HIGH;
        if (score >= 0.4) return RiskLevel.MEDIUM;
        return RiskLevel.LOW;
    }

    private String getRecommendation(double score) {
        if (score >= 0.8) return "REJECT or FLAG transaction";
        if (score >= 0.4) return "REVIEW transaction";
        return "APPROVE transaction";
    }
}
