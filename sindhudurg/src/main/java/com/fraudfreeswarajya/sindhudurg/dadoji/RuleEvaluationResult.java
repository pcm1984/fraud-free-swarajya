package com.fraudfreeswarajya.sindhudurg.dadoji;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.fraudfreeswarajya.sindhudurg.dadoji.RuleOutcome;

@Data
@AllArgsConstructor
public class RuleEvaluationResult {
    private boolean triggered;
    private RuleOutcome outcome;
    private String explanation;
    private RiskRuleType ruleType;
}
