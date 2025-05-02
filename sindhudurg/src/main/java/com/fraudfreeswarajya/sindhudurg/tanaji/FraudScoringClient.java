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

    public FraudScoreResponse getFraudScore(TransactionRequest request) {
        FraudScoreRequest tanajiRequest = mapToTanajiRequest(request);

        FraudScoreResponse response = tanajiWebClient.post()
                .uri("/score")
                .bodyValue(tanajiRequest)
                .retrieve()
                .bodyToMono(FraudScoreResponse.class)
                .block();

        return response;
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
}


