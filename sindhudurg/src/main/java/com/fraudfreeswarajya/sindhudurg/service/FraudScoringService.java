package com.fraudfreeswarajya.sindhudurg.service;

import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class FraudScoringService {

    private static final Logger log = LoggerFactory.getLogger(FraudScoringService.class);


    public double calculateScore() {
        return Math.random(); // stubbed logic, will later call AI
    }
}
