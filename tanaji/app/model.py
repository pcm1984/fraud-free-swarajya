import joblib
import os
import numpy as np
from app.logger_config import logger
import pandas as pd

MODEL_PATH = os.getenv("MODEL_PATH", "app/fraud_model.pkl")

# Load model at startup
model = joblib.load(MODEL_PATH)

def preprocess(request) -> list:
    logger.info(f"Preprocessing transaction {request.transaction_id}")

    # Basic conversion of request data to feature array
    features = [
        float(request.amount),
        1 if request.location.lower() == "risky" else 0,
        1 if request.ip_address.startswith("192.") else 0  # Dummy IP risk logic
    ]
    logger.info(f"Extracted features: {features}")
    return features

def score_transaction(request):
    features = preprocess(request)
    
    #Build a DataFrame with the same column names as training
    df = pd.DataFrame([features], columns=["amount", "location_risk", "ip_risk"])

    prob = model.predict_proba(df)[0][1]
    logger.info(f"Fraud score for txn {request.transaction_id}: {prob:.4f}")
    return round(prob, 4)

