# OpenRemote Device CRUD Microservice

A Spring Boot microservice for managing IoT devices on the [OpenRemote](https://openremote.io/) platform.
Provides secure RESTful endpoints for device create, read, update, delete and advanced querying—ready for production, containerized, and fully environment-configurable.

---

## 🚀 Features

- Create, update, delete, and retrieve IoT devices via OpenRemote API
- Advanced device querying with flexible filters
- Attribute-only update endpoint
- Full OpenAPI/Swagger documentation
- Robust validation & global exception handling
- 100% unit tested (success and error scenarios)
- Containerized with Docker & Docker Compose
- All configs/secrets can be injected via environment variables

---

## 🧰 Tech Stack

- Java 21
- Spring Boot 3.5.x
- Spring Web, Validation
- Spring Cloud OpenFeign
- JUnit 5, Mockito (unit tests)
- Lombok
- springdoc-openapi (Swagger UI)

---

## 📦 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/openremote-crud.git
cd openremote-crud
```
### 2. Build / Run with Docker Compose
```bash
OPENREMOTE_URL=https://YOUR_SERVER_IP \
OPENREMOTE_CLIENT_ID=YOUR_USER \
OPENREMOTE_CLIENT_SECRET=secret \
docker-compose up --build -d
```
### 3. Access the API
Swagger UI: http://YOUR_SERVER_IP:8080/swagger-ui.html

OpenAPI Spec: http://YOUR_SERVER_IP:8080/v3/api-docs