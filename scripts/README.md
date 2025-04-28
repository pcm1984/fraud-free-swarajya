# 📜 Scripts for Traffic Simulation

This folder contains helpful scripts to simulate realistic transaction traffic for the Swarajya Fraud Detection System.

These tools help:
- Generate API traffic
- Populate dashboards (Prometheus + Grafana)
- Stress test fraud scoring pipelines

---

## 📂 Scripts

| Script | Description |
|--------|-------------|
| `simulate_traffic.sh` | Simple Bash script to send 20 fraud score requests to the Sindhudurg API, 1 request per second. |
| `traffic_generator.py` | Python script to send 100 dynamically generated fraud score requests to the Sindhudurg API, at half-second intervals. |

---

## 🚀 How to Run

### Bash Script

```bash
chmod +x scripts/simulate_traffic.sh
./scripts/simulate_traffic.sh

