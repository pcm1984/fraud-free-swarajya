from fastapi import FastAPI
from pydantic import BaseModel
from app import model
from typing import optional
from datetime import datetime
from app.logger_config import logger

from fastapi import HTTPException


app = FastAPI(title="Tanaji - AI Fraud Scorer", version="0.1")

class TransactionRequest(BaseModel):
    transaction_id: str
    user_id: str
    amount: float
    currency: str
    payment_method: str
    card_number_last4: str
    transaction_time: datetime
    location: str
    device_id: str
    ip_address: str
    merchant_id: str

class FraudScoreResponse(BaseModel):
    transaction_id: str
    fraud_score: float
    explanation: list[str]
    risk_indicators: list[str]

@app.post("/score", response_model=FraudScoreResponse)
def score_transaction(request: TransactionRequest):
    try:
        logger.info(f"Received fraud scoring request for {request.transaction_id}")
        score = model.score_transaction(request)

        explanation = []
        risk_indicators = []
        if score > 0.8:
            explanation.append("High risk score")
        if request.amount > 1000:
            explanation.append("High amount")
            risk_indicators.append("HIGH_AMOUNT")

        logger.info(f"Responding with score {score},risk_indicators:{risk_indicators},  explanation: {explanation}")

        response =  FraudScoreResponse(
            transaction_id=request.transaction_id,
            fraud_score=score,
            explanation=explanation,
            risk_indicators=risk_indicators
        ) 
    
        # Explicit log with unpacked attributes
        logger.info("🟡 [SCORING COMPLETE] Transaction ID: %s | Score: %.2f | Explanation: %s | Risk Indicators: %s",
            response.transaction_id,
            response.fraud_score,
            response.explanation,
            response.risk_indicators)

        # Clean structured logging with model_dump
        logger.info("🟣 [RESPONSE SENT] %s", response.model_dump())

        return response

    except Exception:
        logger.exception(f"Scoring failed for transaction: {request.transaction_id}")
        raise HTTPException(status_code=500, detail="Internal fraud model error") 
