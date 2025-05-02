
# 🏥 Patient Management Microservice System

A scalable, cloud-ready microservices architecture for managing patient records, billing, and analytics in a healthcare setting. Built with **Java Spring Boot**, it integrates **gRPC**, **Kafka**, **Docker**, and **AWS** for seamless communication, event-driven processing, and deployment.

---

## 📚 Table of Contents

* [Features](#features)
* [Architecture](#architecture)
* [Tech Stack](#tech-stack)
* [Project Structure](#project-structure)
* [Environment Variables](#environment-variables)
* [Getting Started](#getting-started)
* [API Endpoints](#api-endpoints)

  * [Auth-Service](#auth-service)
  * [Patient-Service](#patient-service)
* [Inter-Service Communication](#inter-service-communication)
* [Deployment](#deployment)
* [Testing](#testing)
* [Contributing](#contributing)
* [License](#license)

---

## ✅ Features

* **Modular Microservices**: Independent services for authentication, patient management, billing, analytics, and API gateway.
* **Secure Authentication**: JWT-based authentication and authorization via `auth-service`.
* **Efficient Communication**:

  * **gRPC** between `patient-service` and `billing-service`.
  * **Kafka** for event-driven communication with `analytics-service`.
* **API Gateway**: Centralized entry point for routing and security.
* **Containerization**: Dockerized services for easy deployment.
* **Cloud-Ready**: Configurations for AWS deployment.

---

## 🏗️ Architecture

```plaintext
[Client]
   |
   v
[API Gateway] ---> [Auth-Service] ---> [JWT Validation]
   |
   v
[Patient-Service] ---> [Billing-Service (gRPC)]
   |
   v
[Kafka] ---> [Analytics-Service]
```

---

## 🛠️ Tech Stack

| Component        | Technology           |
| ---------------- | -------------------- |
| Language         | Java 17              |
| Framework        | Spring Boot          |
| Database         | PostgreSQL           |
| Messaging        | Apache Kafka         |
| RPC              | gRPC                 |
| API Gateway      | Spring Cloud Gateway |
| Authentication   | JWT                  |
| Containerization | Docker               |
| Cloud Deployment | AWS                  |

---

## 📁 Project Structure

```plaintext
Patient-Management-System/
├── api-gateway/           # Entry point for all client requests
├── auth-service/          # Handles authentication and JWT issuance
├── patient-service/       # Manages patient data and operations
├── billing-service/       # Processes billing via gRPC
├── analytics-service/     # Consumes Kafka events for analytics
├── api-request/           # Shared DTOs and request models
├── infrastructure/        # Docker, AWS configs, and deployment resources
├── integration-tests/     # System-wide integration test suite
└── test-data/             # Sample test data for development/testing
```

---

## 🔐 Environment Variables

### Patient-Service

```env
BILLING_SERVICE_ADDRESS=billing-service
BILLING_SERVICE_GRPC_PORT=9005
JAVA_TOOL_OPTIONS=-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005
SPRING_DATASOURCE_URL=jdbc:postgresql://patient-service-db:5432/db
SPRING_DATASOURCE_USERNAME=admin_user
SPRING_DATASOURCE_PASSWORD=password
SPRING_JPA_HIBERNATE_DDL_AUTO=update
SPRING_SQL_INIT_MODE=always
SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka:9092
```

### Auth-Service

```env
SPRING_DATASOURCE_URL=jdbc:postgresql://auth-service-db:5432/db
SPRING_DATASOURCE_USERNAME=admin_user
SPRING_DATASOURCE_PASSWORD=password
SPRING_JPA_HIBERNATE_DDL_AUTO=update
SPRING_SQL_INIT_MODE=always
```

### Auth-Service DB

```env
POSTGRES_DB=db
POSTGRES_USER=admin_user
POSTGRES_PASSWORD=password
```

### Analytics-Service

```env
SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka:9092
```

---

## 🚀 Getting Started

### Prerequisites

* Java 21
* Docker & Docker Compose
* PostgreSQL
* Kafka & Zookeeper (Dockerized)
* Maven

### Running the Application

1. **Clone the repository:**

   ```bash
   git clone https://github.com/aansh06/Patient-Management-System.git
   cd Patient-Management-System
   ```

2. **Start all services using Docker Compose:**

   ```bash
   docker-compose up --build
   ```

3. **Access the services:**

   * API Gateway: `http://localhost:8080`
   * Swagger UI: `http://localhost:8080/swagger-ui.html`

---

## 📬 API Endpoints

### Auth-Service

* **Login**

  * **URL:** `/login`

  * **Method:** `POST`

  * **Request Body:**

    ```json
    {
      "username": "user@example.com",
      "password": "password123"
    }
    ```

  * **Response:**

    ```json
    {
      "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6..."
    }
    ```

* **Validate Token**

  * **URL:** `/validate`

  * **Method:** `GET`

  * **Headers:**

    ```
    Authorization: Bearer <token>
    ```

  * **Response:** `200 OK` if valid, `401 Unauthorized` if invalid.

### Patient-Service

* **Get All Patients**

  * **URL:** `/patients`

  * **Method:** `GET`

  * **Headers:**

    ```
    Authorization: Bearer <token>
    ```

  * **Response:**

    ```json
    [
      {
        "id": "uuid",
        "name": "John Doe",
        "email": "john.doe@example.com",
        "address": "123 Main St",
        "dateOfBirth": "1990-01-01",
        "registeredDate": "2025-01-01"
      },
      ...
    ]
    ```

* **Create a New Patient**

  * **URL:** `/patients`

  * **Method:** `POST`

  * **Headers:**

    ```
    Authorization: Bearer <token>
    ```

  * **Request Body:**

    ```json
    {
      "name": "Jane Doe",
      "email": "jane.doe@example.com",
      "address": "456 Elm St",
      "dateOfBirth": "1992-02-02",
      "registeredDate": "2025-02-02"
    }
    ```

  * **Response:**

    ```json
    {
      "id": "uuid",
      "name": "Jane Doe",
      "email": "jane.doe@example.com",
      "address": "456 Elm St",
      "dateOfBirth": "1992-02-02",
      "registeredDate": "2025-02-02"
    }
    ```

* **Update a Patient**

  * **URL:** `/patients/{id}`

  * **Method:** `PUT`

  * **Headers:**

    ```
    Authorization: Bearer <token>
    ```

  * **Request Body:** Same as "Create a New Patient"

  * **Response:** Updated patient object.

* **Delete a Patient**

  * **URL:** `/patients/{id}`

  * **Method:** `DELETE`

  * **Headers:**

    ```
    Authorization: Bearer <token>
    ```

  * **Response:** `204 No Content` on successful deletion.

---

## 🔗 Inter-Service Communication

* **gRPC:** `patient-service` communicates with `billing-service` for billing operations.
* **Kafka:** `patient-service` publishes events to Kafka topics consumed by `analytics-service` for real-time analytics.

---

## ☁️ Deployment

### AWS Deployment

1. **Build Docker Images:**

   ```bash
   docker build -t your-dockerhub-username/patient-service ./patient-service
   # Repeat for other services
   ```

2. **Push to Docker Hub:**

   ```bash
   docker push your-dockerhub-username/patient-service
   # Repeat for other services
   ```

3. **Deploy on AWS:**

   * Use AWS ECS or EC2 instances to deploy the Docker containers.
   * Configure environment variables as per the [Environment Variables](#environment-variables) section.
   * Set up AWS RDS for PostgreSQL databases.
   * Use AWS MSK or self-managed Kafka clusters.

---

## 🧪 Testing

### Integration Tests

Run all integration tests using Maven:

```bash
mvn clean verify -Pintegration-tests
```

### API Testing

Use tools like **Postman** or **curl** to test the API endpoints. Ensure to include the JWT token in the `Authorization` header for secured endpoints.

---

## 🤝 Contributing

Contributions are welcome! Please fork the repository and submit a pull request for any enhancements or bug fixes.

---

## 📄 License

This project is licensed under the MIT License.
