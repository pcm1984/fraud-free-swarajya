from pydantic import BaseModel

class TransactionRequest(BaseModel):
    transaction_id: str
    user_id: str
    amount: float
    currency: str
    location: str
    ip_address: str
    merchant_id: str
    timestamp: str

