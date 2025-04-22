from fastapi.testclient import TestClient
from app.main import app

client = TestClient(app)

def test_score_endpoint_200():
    response = client.post("/score", json={
        "transaction_id": "txn002",
        "user_id": "user2",
        "amount": 5000,
        "currency": "INR",
        "location": "risky",
        "ip_address": "192.168.0.1",
        "merchant_id": "m002",
        "timestamp": "2025-04-21T12:00:00Z"
    })

    assert response.status_code == 200
    data = response.json()
    assert "fraud_score" in data
    assert 0.0 <= data["fraud_score"] <= 1.0
    assert "transaction_id" in data

