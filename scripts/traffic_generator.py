import requests
import time
import random

API_URL = "http://localhost:8080/api/v1/fraud-score"

def generate_transaction(i):
    payload = {
        "transactionId": f"txn{i}",
        "amount": f"{random.randint(1, 1000)}.00",
        "currency": "USD",
        "transactionTime": "2025-04-27T18:00:00Z",
        "merchantId": f"merchant{i}",
        "location": f"Location{i}",
        "userId": f"user{i}",
        "paymentMethod": "card"
    }
    return payload

if __name__ == "__main__":
    for i in range(1, 101):  # Sends 100 transactions
        data = generate_transaction(i)
        response = requests.post(API_URL, json=data)
        print(f"✅ Sent transaction {i}, Status: {response.status_code}")
        time.sleep(0.5)  # Half a second between each request

