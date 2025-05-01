package com.fraudfreeswarajya.sindhudurg.dadoji.rules;

import com.fraudfreeswarajya.sindhudurg.dto.TransactionRequest;
import com.fraudfreeswarajya.sindhudurg.dadoji.*;
import com.fraudfreeswarajya.sindhudurg.model.RiskIndicator;

public class HighAmountRule implements RuleEngine {

    private static final double HIGH_AMOUNT_THRESHOLD = 5000.00;

    @Override
    public RuleEvaluationResult evaluate(TransactionRequest request, double fraudScore) {
        if (request.getAmount() > HIGH_AMOUNT_THRESHOLD) {
            return new RuleEvaluationResult(
                    true,
                    RuleOutcome.MANUAL_REVIEW,
                    "High amount flagged: $" + request.getAmount(),
                    RiskRuleType.HIGH_AMOUNT
            );
        }
        return new RuleEvaluationResult(false, RuleOutcome.APPROVE, "", RiskRuleType.HIGH_AMOUNT);
    }

    @Override
    public RiskIndicator getHandledIndicator() {
        return RiskIndicator.HIGH_AMOUNT;
    }
}
