package com.fraudfreeswarajya.sindhudurg.controller;

import com.fraudfreeswarajya.sindhudurg.dto.TransactionRequest;
import com.fraudfreeswarajya.sindhudurg.dto.TransactionResponse;
import com.fraudfreeswarajya.sindhudurg.service.FraudScoringService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.fraudfreeswarajya.sindhudurg.model.RiskLevel;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/v1")
public class FraudScoringController {

    private static final Logger log = LoggerFactory.getLogger(FraudScoringController.class);

    private final FraudScoringService fraudScoringService;

    public FraudScoringController(FraudScoringService fraudScoringService){
        this.fraudScoringService = fraudScoringService;
    }

    @PostMapping("/fraud-score")
    public TransactionResponse scoreTransaction(@Valid @RequestBody TransactionRequest request) {
        return fraudScoringService.processTransaction(request);
    }
}
