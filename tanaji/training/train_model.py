import pandas as pd
from sklearn.ensemble import RandomForestClassifier
from sklearn.model_selection import train_test_split
from sklearn.metrics import classification_report
import joblib

# Step 1: Create dummy dataset
data = pd.DataFrame([
    {"amount": 99, "location_risk": 1, "ip_risk": 0, "is_fraud": 0},
    {"amount": 1000, "location_risk": 1, "ip_risk": 1, "is_fraud": 1},
    {"amount": 150, "location_risk": 0, "ip_risk": 0, "is_fraud": 0},
    {"amount": 5000, "location_risk": 1, "ip_risk": 1, "is_fraud": 1},
    {"amount": 200, "location_risk": 0, "ip_risk": 1, "is_fraud": 0},
    {"amount": 7000, "location_risk": 1, "ip_risk": 0, "is_fraud": 1},
])

X = data[["amount", "location_risk", "ip_risk"]]
y = data["is_fraud"]

# Step 2: Train model
model = RandomForestClassifier(n_estimators=100, random_state=42)
model.fit(X, y)

# Step 3: Evaluate
print(classification_report(y, model.predict(X)))

# Step 4: Save model
joblib.dump(model, "app/fraud_model.pkl")

