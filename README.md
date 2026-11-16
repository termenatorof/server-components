# Лабораторна робота №1: Технології розроблення серверних компонентів

Веб-застосунок на Java 21 (Spring Boot) із системними ендпоінтами перевірки стану (Liveness/Readiness), мультистадійною контейнеризацією Docker та CI-пайплайном GitHub Actions.

## Системні ендпоінти (Healthcheck API)

* **Liveness probe:** `GET http://localhost:8080/healthz/live` — перевіряє життєздатність процесу застосунку.
* **Readiness probe:** `GET http://localhost:8080/healthz/ready` — перевіряє готовність сервісу до обробки мережевого трафіку.

## Локальний запуск через Docker Compose

### Передумови
* Встановлений **Docker** та **Docker Compose** (наприклад, Docker Desktop для Windows/macOS або Docker Engine для Linux).

### Інструкція із запуску
1. Склонуйте репозиторій та перейдіть у директорію проєкту:
   ```bash
   git clone [https://github.com/termenatorof/server-components.git](https://github.com/termenatorof/server-components.git)
   cd server-components