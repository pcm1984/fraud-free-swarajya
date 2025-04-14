package com.fraudfreeswarajya.sindhudurg.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fraudfreeswarajya.sindhudurg.dto.TransactionResponse;
import com.fraudfreeswarajya.sindhudurg.model.RiskLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RedisCacheServiceTest {

    private StringRedisTemplate redisTemplate;
    private ObjectMapper objectMapper;
    private RedisCacheService redisCacheService;

    private final String KEY = "fraud:txn123";

    @BeforeEach
    void setup() {
        redisTemplate = mock(StringRedisTemplate.class);
        objectMapper = new ObjectMapper();
        redisCacheService = new RedisCacheService(redisTemplate, objectMapper);
    }

    @Test
    void shouldSerializeAndStoreTransactionResponse() throws Exception {
        ValueOperations<String, String> ops = mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(ops);

        TransactionResponse response = TransactionResponse.builder()
                .transactionId("txn123")
                .fraudScore(0.88)
                .riskLevel(RiskLevel.HIGH)
                .recommendation("REJECT")
                .explanation(List.of("High amount", "Foreign IP"))
                .build();

        redisCacheService.save(KEY, response, 300);

        verify(ops).set(eq(KEY), anyString(), any());
    }
}
