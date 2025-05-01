package com.fraudfreeswarajya.sindhudurg.dadoji;

import com.fraudfreeswarajya.sindhudurg.dto.TransactionRequest;
import com.fraudfreeswarajya.sindhudurg.model.RiskIndicator;

public interface RuleEngine {
    RuleEvaluationResult evaluate(TransactionRequest request, double fraudScore);
    RiskIndicator getHandledIndicator(); // ✅ new method
}
