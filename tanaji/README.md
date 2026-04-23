# 🧠 Tanaji – AI Fraud Scoring Engine

> *“Courage in execution. Precision in action.”*

Tanaji is the **intelligence layer** of Fraud-Free Swarajya.
In this service, I focus on detecting fraudulent transactions using AI, while keeping the system **explainable, practical, and architecturally sound**.

---

# ⚔️ Overview

`tanaji` is a Python-based microservice (FastAPI) that evaluates transactions and returns:

* Fraud Score (0 → 1)
* Risk Indicators
* Human-readable Explanation

It is invoked by `sindhudurg` (API layer) and works alongside `dadoji` (rules engine) to form a **hybrid fraud detection system**.

---

# 🏗️ Current State (MVP)

At this stage, Tanaji is **not yet a trained ML system**.

### ✅ What I have built so far:

* REST API (`/score`)
* Deterministic / heuristic scoring logic
* Basic explanation output
* Integration with:

  * `sindhudurg`
  * `dadoji`
* Dockerized and runnable via Docker Compose

### ⚠️ Current limitations:

* No trained ML model
* No dataset
* No feature engineering pipeline
* No evaluation metrics (precision/recall)

👉 Right now, Tanaji simulates AI behavior — the architecture is ready, but the intelligence is still evolving.

---

# 🚧 What I Am Working On

## 🎯 Goal: Evolve Tanaji into a Real ML-Based Fraud Engine

The next step is to replace heuristic logic with a **real, trained machine learning model**.

---

## 📊 Dataset (In Progress)

I am building a **synthetic transaction dataset** that reflects real-world fraud patterns while keeping the system simple and explainable.

Each record represents a transaction with:

### 🔹 Raw Features

* amount
* location
* device_id
* merchant_id
* timestamp

### 🔹 Derived Features (where real ML value comes in)

* txn_count_1h (velocity)
* txn_count_24h
* avg_txn_amount_7d
* amount_deviation
* is_risky_location
* device_change_flag
* merchant_risk_score

### 🔹 Label

* `fraud` → 0 or 1

Example:

```json
{
  "amount": 12000,
  "txn_count_1h": 6,
  "avg_txn_amount_7d": 2000,
  "amount_deviation": 5.0,
  "is_risky_location": 1,
  "device_change_flag": 1,
  "merchant_risk_score": 0.8,
  "fraud": 1
}
```

---

# 🧭 Target Architecture (Post ML Integration)

```
Client
  ↓
Sindhudurg (Java API)
  ↓
Tanaji (ML Model)
  ↓
Dadoji (Rules Engine)
  ↓
Final Decision + Explanation
```

My goal is to keep Tanaji focused on **probability and pattern detection**, while Dadoji handles **business rules and overrides**.

---

# 🚀 Planned ML Capabilities (Minimal, but Real)

I am intentionally focusing on **core ML concepts that matter in real fraud systems**, without over-engineering.

---

## ✅ 1. Real ML Model

I plan to use a **tree-based model** (Random Forest / XGBoost).

Why:

* Works well with tabular data
* Handles non-linear relationships
* Easier to interpret compared to deep learning models

---

## ✅ 2. Feature Engineering

Instead of complex pipelines, I am focusing on **practical, high-signal features**:

* Transaction amount deviation
* Transaction velocity (1h / 24h)
* Risky location flags
* Device change detection
* Merchant risk score

This is where most of the model’s effectiveness will come from.

---

## ✅ 3. Class Imbalance Handling

Fraud is rare in real systems (~2–5%).

I will handle this using:

* Class weights (simple and effective)

---

## ✅ 4. Precision vs Recall Trade-off

Fraud detection is not a generic ML problem.

* Missing fraud (false negatives) is expensive → prioritize **Recall**
* But too many false alarms hurt UX → control **Precision**

I plan to:

* Tune prediction thresholds
* Document trade-offs clearly

---

## ✅ 5. Explainability (Fintech-Oriented)

Explainability is not optional in fraud systems.

I will ensure that each prediction includes:

* Feature importance / contribution
* Human-readable reasoning

Example:

```
High transaction amount and risky location increased fraud risk
```

---

## ✅ 6. Avoiding Data Leakage

Even in a synthetic setup, I am enforcing:

* Features derived using **past data only**
* Clear separation between:

  * Training data
  * Inference logic

This is critical for building trust in the model.

---

## ✅ 7. Versioning (Lightweight)

To keep things simple but structured:

* model_version → v1, v2
* feature_version → v1

Stored as metadata/config.

---

## ✅ 8. Basic ML Monitoring

I will extend `kanhoji` (monitoring) to track:

* Prediction distribution
* Percentage of high-risk transactions
* Basic drift indicators (optional)

---

# ⚠️ What I Am Not Doing (By Design)

To stay focused and practical, I am intentionally avoiding:

* Full feature store infrastructure
* Complex MLOps pipelines
* Streaming ML systems
* Heavy experimentation frameworks

The goal is to demonstrate **clarity, correctness, and architectural thinking** — not scale for its own sake.

---

# ⚔️ Tanaji + Dadoji = Hybrid Intelligence

```
Tanaji (AI Model)
   ↓
Fraud Probability

Dadoji (Rules Engine)
   ↓
Business Rules + Overrides

Final Output
   ↓
Explainable Decision
```

This hybrid approach reflects how real-world fintech systems balance **AI flexibility with rule-based control**.

---

# 🛤️ Roadmap

### Phase 1

* Generate dataset
* Design features
* Train first ML model

### Phase 2

* Integrate model into API
* Add explainability
* Tune thresholds

### Phase 3

* Extend monitoring
* Add versioning
* Improve documentation

---

# 🧠 Key Takeaway

Tanaji is evolving from:

> “A system that simulates AI”

to:

> **“A system designed to host real, explainable, production-ready ML”**

---

# 🇮🇳 Closing Thought

> *“It is not enough to be brave in battle. One must also be intelligent.”*

This service is my attempt to build that intelligence — step by step.

---

