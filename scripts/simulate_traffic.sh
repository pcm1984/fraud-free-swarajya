#!/bin/bash

# Bash script to simulate multiple transactions to Sindhudurg API

for i in {1..20}
do
  curl -X POST http://localhost:8080/api/v1/fraud-score \
    -H "Content-Type: application/json" \
    -d "{
      \"transactionId\": \"txn$i\",
      \"amount\": \"$((RANDOM % 1000)).00\",
      \"currency\": \"USD\",
      \"transactionTime\": \"2025-04-27T18:00:00Z\",
      \"merchantId\": \"merchant$i\",
      \"location\": \"Location$i\",
      \"userId\": \"user$i\",
      \"paymentMethod\": \"card\",
      \"ipAddress\": \"1.2.3.4\",
      \"deviceId\": \"d123\",
      \"cardNumberLast4\": \"1234\"
    }"
  echo "✅ Sent transaction $i"
  sleep 1
done

