# Kanhoji Monitoring System: Observability with Prometheus and Grafana

> "No fortress is safe unless its walls are watched."  
> — Inspired by Kanhoji Angre, Swarajya's Guardian of the Seas ⚓

---

## 📈 Purpose

This document explains how we set up real-time observability for **Sindhudurg**, **Tanaji**, and **Ramchandrapant** services using **Prometheus** for metrics collection and **Grafana** for visualization.

**Goal:**  
- Real-time API health checks
- JVM and memory monitoring
- Database connection tracking
- Future-ready custom metrics (e.g., fraud scoring trends)

---

## ⚙️ Setup Overview

| Component        | Role                                    |
|------------------|-----------------------------------------|
| Prometheus       | Metrics collector and time-series store |
| Grafana          | Visual dashboard frontend               |
| Spring Boot Actuator | Exposes internal metrics at `/actuator/prometheus` |
| Micrometer       | Bridges Spring Boot metrics to Prometheus |

---

## 🏗️ Architecture Diagram


