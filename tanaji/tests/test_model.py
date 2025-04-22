from app import model
from app.schema import TransactionRequest

def test_score_transaction_basic():
    request = TransactionRequest(
        transaction_id="txn001",
        user_id="user1",
        amount=1000,
        currency="INR",
        location="risky",
        ip_address="192.168.0.1",
        merchant_id="m001",
        timestamp="2025-04-21T10:00:00Z"
    )

    score = model.score_transaction(request)
    
    assert isinstance(score, float)
    assert 0.0 <= score <= 1.0

