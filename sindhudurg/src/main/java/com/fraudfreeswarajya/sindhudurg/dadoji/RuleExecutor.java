package com.fraudfreeswarajya.sindhudurg.dadoji;

import com.fraudfreeswarajya.sindhudurg.dadoji.dto.RuleDecision;
import com.fraudfreeswarajya.sindhudurg.dto.TransactionRequest;
import com.fraudfreeswarajya.sindhudurg.dadoji.rules.*;
import com.fraudfreeswarajya.sindhudurg.model.RiskIndicator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RuleExecutor {

    private final List<RuleEngine> rules = List.of(
            new HighAmountRule()
            //new RiskyCountryRule(),
            //new VelocityRule()
            // Add more rules here!
    );

    public RuleDecision evaluateAll(TransactionRequest request, double fraudScore, List<RiskIndicator> aiIndicators) {
        Set<RiskIndicator> aiIndicatorSet = new HashSet<>(aiIndicators);
        RuleOutcome finalOutcome = RuleOutcome.APPROVE;
        List<String> explanations = new ArrayList<>();
        List<RiskRuleType> triggeredRules = new ArrayList<>();

        for (RuleEngine rule : rules) {
            if (aiIndicatorSet.contains(rule.getHandledIndicator())) {
                // ✅ Skip if AI already handled it
                System.out.printf("Skipping rule %s — already handled by AI%n", rule.getHandledIndicator());
                continue;
            }
            RuleEvaluationResult result = rule.evaluate(request, fraudScore);
            if (result.isTriggered()) {
                explanations.add(result.getExplanation());
                triggeredRules.add(result.getRuleType());

                if (result.getOutcome() == RuleOutcome.REJECT) {
                    finalOutcome = RuleOutcome.REJECT;
                    break; // Stop on REJECT
                } else if (result.getOutcome() == RuleOutcome.MANUAL_REVIEW && finalOutcome != RuleOutcome.REJECT) {
                    finalOutcome = RuleOutcome.MANUAL_REVIEW;
                }
            }
        }

        return new RuleDecision(finalOutcome, explanations, triggeredRules);
    }
}
