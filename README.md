# Spring Boot Observability with Grafana, Loki, Prometheus, and OpenTelemetry

🚀 This project demonstrates how to add **end-to-end observability** to a Spring Boot application using **Grafana**, **Loki**, **Prometheus**, and **OpenTelemetry**.

---

## 📌 Key Features

- ✅ Structured logging with Loki
- 📈 Metrics collection with Prometheus
- 🧠 Tracing with OpenTelemetry
- 📊 Visual dashboards in Grafana
- 📦 Docker Compose setup for quick start

---

## 🔍 Why Observability?

Modern microservices need more than just logs – you need **logs, metrics, and traces** to truly **observe, debug, and optimize** your application performance. This repo helps you build a robust observability stack for any Spring Boot-based system.

---

## 📚 Table of Contents

1. [Tech Stack](#tech-stack)
2. [Architecture Overview](#architecture-overview)
3. [Setup Instructions](#setup-instructions)
4. [How to View Logs, Metrics & Traces](#how-to-view-logs-metrics--traces)
5. [Custom Metrics and Alerts](#custom-metrics-and-alerts)
6. [FAQ](#faq)

---

## 🛠️ Tech Stack

- Java 17
- Spring Boot 3.x
- Grafana
- Loki
- Prometheus
- OpenTelemetry
- Docker & Docker Compose

---

## 🧩 Architecture Overview

```plaintext
Spring Boot App
 ├── Logs → Loki → Grafana
 ├── Metrics → Prometheus → Grafana
 └── Traces → OpenTelemetry Collector → Grafana Tempo (optional)
