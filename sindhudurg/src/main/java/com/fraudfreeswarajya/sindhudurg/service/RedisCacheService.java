package com.fraudfreeswarajya.sindhudurg.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fraudfreeswarajya.sindhudurg.dto.TransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

@Service
public class RedisCacheService {

    private static final Logger log = LoggerFactory.getLogger(RedisCacheService.class);

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public RedisCacheService(StringRedisTemplate redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    public void save(String key, TransactionResponse response, long ttlInSeconds) {
        try {
            String json = objectMapper.writeValueAsString(response);
            redisTemplate.opsForValue().set(key, json, Duration.ofSeconds(ttlInSeconds));
            log.info("Cached response under key: {} (TTL: {}s)", key, ttlInSeconds);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize response for caching", e);
            throw new RuntimeException("Failed to cache fraud response", e);
        }
    }

    public TransactionResponse get(String key) {
        try {
            String json = redisTemplate.opsForValue().get(key);
            if (json != null) {
                log.info("Redis cache hit for key: {}", key);
                return objectMapper.readValue(json, TransactionResponse.class);
            } else {
                log.info("Redis cache miss for key: {}", key);
                return null;
            }
        } catch (JsonProcessingException e) {
            log.error("Failed to deserialize cached value for key: {}", key, e);
            return null;
        }
    }
}
