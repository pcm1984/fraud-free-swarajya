package com.fraudfreeswarajya.sindhudurg.dadoji.dto;

import com.fraudfreeswarajya.sindhudurg.dadoji.RuleOutcome;
import com.fraudfreeswarajya.sindhudurg.dadoji.RiskRuleType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RuleDecision {
    private RuleOutcome outcome;
    private List<String> explanations;
    private List<RiskRuleType> triggeredRules;
}
