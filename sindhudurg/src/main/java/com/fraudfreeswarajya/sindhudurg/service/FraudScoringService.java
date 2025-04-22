package com.fraudfreeswarajya.sindhudurg.service;

import com.fraudfreeswarajya.sindhudurg.dto.TransactionRequest;
import com.fraudfreeswarajya.sindhudurg.dto.TransactionResponse;
import com.fraudfreeswarajya.sindhudurg.tanaji.FraudScoringClient;
import com.fraudfreeswarajya.sindhudurg.tanaji.dto.FraudScoreResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class FraudScoringService {

    private static final Logger log = LoggerFactory.getLogger(FraudScoringService.class);

    @Autowired
    private FraudScoringClient fraudScoringClient;


    public TransactionResponse processTransaction(TransactionRequest request) {


        TransactionResponse response = fraudScoringClient.getFraudScore(request);

        log.info("Fraud score for txn {}: {} - Explanation: {}",
                response.getTransactionId(),
                response.getFraudScore(),
                response.getExplanation());
        return response;
    }
}
