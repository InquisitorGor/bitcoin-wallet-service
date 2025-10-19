# Bitcoin Wallet Service

A Spring Boot microservice that tracks Bitcoin wallet wealth over time, processing incoming Bitcoin transactions and maintaining hourly cumulative balance statistics.

## Overview

This service handles Bitcoin wallet top-up events from various time zones and maintains a historical record of wallet balance at the end of each hour. It's designed for users who want to track and display their wallet's wealth history without withdrawing funds.

### Key Features

- **Real-time Transaction Processing**: Processes Bitcoin top-up events as they arrive
- **Hourly Balance Tracking**: Maintains cumulative wallet balance for each hour
- **Multi-timezone Support**: Handles transactions from different time zones
- **RESTful API**: Provides endpoints to query historical balance data
- **Event-driven Architecture**: Uses Kafka for asynchronous event processing

## Architecture

The service consists of several components:

- **Top-up Controller**: REST endpoint for receiving Bitcoin transactions
- **Event Processing**: Kafka-based event handling for scalability
- **Statistics Service**: Processes events and maintains hourly balance records
- **Cumulative Balance API**: REST endpoint for querying historical balance data

## Data Model

### Transaction Event
```json
{
  "datetime": "2019-10-05T14:48:01+01:00",
  "amount": 1.1
}
```

### Cumulative Balance Response
```json
[
  {
    "datetime": "2019-10-05T12:00:00+00:00",
    "amount": 1000
  },
  {
    "datetime": "2019-10-05T13:00:00+00:00",
    "amount": 1000
  },
  {
    "datetime": "2019-10-05T14:00:00+00:00",
    "amount": 1001.1
  }
]
```

## Prerequisites

- Git
- Docker and Docker Compose
- Java 21 (only for local development)
- Gradle 8.7+ (only for local development)

## Quick Start with Docker

### 1. Clone the Repository

```bash
git clone https://github.com/InquisitorGor/bitcoin-wallet-service.git
cd bitcoin-wallet-service
```

### 2. Environment Setup

Create a `.env` file in the project root:

```bash
# Database Configuration
POSTGRES_DB=bitcoin_wallet
POSTGRES_USER=postgres
POSTGRES_PASSWORD=password
POSTGRES_PORT=5432
POSTGRES_DOCKER_HOST=postgres
POSTGRES_DOCKER_PORT=5432

# Kafka Configuration
KAFKA_PORT=9092
KAFKA_DOCKER_HOST=kafka
KAFKA_DOCKER_PORT=9092
KAFKA_HOST=localhost
KAFKA_DOCKER_HOST=kafka

# Application Configuration
APP_PORT=8080
SPRING_PROFILES_ACTIVE=dev
```

### 3. Launch the Application

```bash
# Start all services (PostgreSQL, Kafka, and the application)
docker compose up -d

# Stop all and remove volumes
docker compose down -v

# View logs
docker compose logs -f bitcoin-wallet-service

# Check service health
docker compose ps
```

### 4. Verify Installation

```bash
# Check if the application is running
curl http://localhost:8080/actuator/health

# The service should be available at:
# http://localhost:8080
```

## API Endpoints

### 1. Submit Bitcoin Transaction

**POST** `/api/topup`

Submit a Bitcoin transaction to the wallet.

```bash
curl -X POST http://localhost:8080/wallet/balance/api/topup \
  -H "Content-Type: application/json" \
  -d '{
    "datetime": "2019-10-05T14:48:01+01:00",
    "amount": 1.1
  }'
```

### 2. Get Cumulative Balance History

**GET** `/api/statistics/cumulative-balance`

Retrieve historical wallet balance data for a specific time range.

```bash
curl "http://localhost:8080/api/statistics/cumulative-balance?from=2019-10-05T00:00:00Z&to=2019-10-05T23:59:59Z"
```

**Parameters:**
- `from` (required): Start datetime in ISO format
- `to` (required): End datetime in ISO format

**Response:**
```json
[
  {
    "datetime": "2019-10-05T12:00:00+00:00",
    "amount": 1000
  },
  {
    "datetime": "2019-10-05T13:00:00+00:00",
    "amount": 1000
  },
  {
    "datetime": "2019-10-05T14:00:00+00:00",
    "amount": 1001.1
  }
]
```

## Database Schema

The service uses PostgreSQL with the following tables:

### `top_up_event`
Stores individual Bitcoin transaction events.

### `hourly_deltas`
Stores cumulative wallet balance at the end of each hour.

### `wallet`
Maintains the current wallet state and balance.

## Development

### Local Development Setup

1. **Start Infrastructure Services:**
   ```bash
   # Start only PostgreSQL and Kafka
   docker-compose up -d postgres kafka
   ```

2. **Run the Application:**
   ```bash
   ./gradlew bootRun
   ```

### Building the Application

```bash
# Build the application
./gradlew build

# Run tests
./gradlew test

# Create Docker image
docker build -t bitcoin-wallet-service .
```

## Configuration

The application uses the following configuration files:

- `application.yaml`: Main application configuration
- `docker-compose.yml`: Docker services configuration
- `Dockerfile`: Application container definition

## Monitoring and Health Checks

The service includes health checks for all components:

- **Application Health**: `http://localhost:8080/actuator/health`
- **Database Health**: Built-in PostgreSQL health checks
- **Kafka Health**: Built-in Kafka broker health checks

## Troubleshooting


### Logs

```bash
# View all service logs
docker-compose logs

# View specific service logs
docker-compose logs bitcoin-wallet-service
docker-compose logs postgres
docker-compose logs kafka
```

### Reset Environment

```bash
# Stop and remove all containers
docker-compose down -v

# Remove all data volumes
docker-compose down -v --remove-orphans

# Start fresh
docker-compose up -d
```
