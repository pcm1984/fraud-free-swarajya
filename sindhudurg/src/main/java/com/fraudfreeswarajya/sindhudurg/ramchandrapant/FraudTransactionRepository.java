package com.fraudfreeswarajya.sindhudurg.ramchandrapant;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface FraudTransactionRepository extends JpaRepository<FraudTransactionEntity, UUID> {
    Optional<FraudTransactionEntity> findByTransactionId(String txnId);
}

