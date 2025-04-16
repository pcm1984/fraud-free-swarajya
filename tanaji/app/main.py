from fastapi import FastAPI
from pydantic import BaseModel

app = FastAPI(title="Tanaji - AI Fraud Scorer", version="0.1")

class TransactionRequest(BaseModel):
    transaction_id: str
    user_id: str
    amount: float
    currency: str
    location: str
    ip_address: str
    merchant_id: str
    timestamp: str

class FraudScoreResponse(BaseModel):
    transaction_id: str
    fraud_score: float
    explanation: list[str]

@app.post("/score", response_model=FraudScoreResponse)
def score_transaction(request: TransactionRequest):
    # Stubbed logic
    dummy_score = 0.83
    return FraudScoreResponse(
        transaction_id=request.transaction_id,
        fraud_score=dummy_score,
        explanation=["High amount", "Foreign IP"]
    )

