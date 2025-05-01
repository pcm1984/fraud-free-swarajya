package com.fraudfreeswarajya.sindhudurg.tanaji;

import com.fraudfreeswarajya.sindhudurg.dto.TransactionRequest;
import com.fraudfreeswarajya.sindhudurg.dto.TransactionResponse;
import com.fraudfreeswarajya.sindhudurg.model.RiskLevel;
import com.fraudfreeswarajya.sindhudurg.tanaji.dto.FraudScoreRequest;
import com.fraudfreeswarajya.sindhudurg.tanaji.dto.FraudScoreResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class FraudScoringClient {

    private final WebClient tanajiWebClient;

    public TransactionResponse getFraudScore(TransactionRequest request) {
        FraudScoreRequest tanajiRequest = mapToTanajiRequest(request);

        FraudScoreResponse response = tanajiWebClient.post()
                .uri("/score")
                .bodyValue(tanajiRequest)
                .retrieve()
                .bodyToMono(FraudScoreResponse.class)
                .block();

        return mapToTransactionResponse(response);
    }

    private FraudScoreRequest mapToTanajiRequest(TransactionRequest req) {
        return new FraudScoreRequest(
                req.getTransactionId(),
                req.getUserId(),
                req.getAmount(),
                req.getCurrency(),
                req.getLocation(),
                req.getIpAddress(),
                req.getMerchantId(),
                req.getTransactionTime().toString()
        );
    }

    private TransactionResponse mapToTransactionResponse(FraudScoreResponse res) {
        RiskLevel risk = getRiskLevel(res.getFraud_score());
        String recommendation = getRecommendation(res.getFraud_score());

        return TransactionResponse.builder()
                .transactionId(res.getTransaction_id())
                .fraudScore(res.getFraud_score())
                .riskLevel(risk)
                .explanation(res.getExplanation())
                .recommendation(recommendation)
                .riskIndicators(res.getRisk_indicators())
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


