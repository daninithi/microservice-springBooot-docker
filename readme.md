# Spring Boot Microservices with Docker and PostgreSQL

This project demonstrates a complete microservice architecture using Spring Boot, Docker, and PostgreSQL. It features four independent services orchestrated by an API Gateway and managed with a full CI/CD pipeline in Jenkins.

---
## Architecture Overview
This project consists of four core services that work together to provide a simple user and order management system. All external traffic is routed through a single API Gateway, and services discover each other using a Eureka service registry.

* **API Gateway**: The single entry point for all client requests. It handles routing to the appropriate backend service.
* **Eureka Server**: The service registry that allows microservices to dynamically register and discover each other.
* **User Service**: Manages all user-related CRUD operations and connects to its own dedicated PostgreSQL database.
* **Order Service**: Manages all order-related CRUD operations and connects to its own dedicated PostgreSQL database.

## Core Technologies
* **Backend**: Java 17, Spring Boot 3
* **Service Discovery**: Spring Cloud Eureka
* **API Gateway**: Spring Cloud Gateway
* **Database**: PostgreSQL
* **ORM**: Spring Data JPA (Hibernate)
* **Containerization**: Docker & Docker Compose
* **CI/CD**: Jenkins (Declarative Pipeline)
* **Build Tool**: Apache Maven

---
## Project Structure
```
microservice-springBooot-docker/
├── docker-compose.yml        # Defines and runs the entire multi-container application
├── Jenkinsfile               # The CI/CD pipeline definition for Jenkins
├── api-gateway-service/      # Spring Cloud Gateway service
│   ├── src/
│   ├── Dockerfile
│   └── pom.xml
├── eureka-server/            # Spring Cloud Eureka service
│   ├── src/
│   ├── Dockerfile
│   └── pom.xml
├── order-service/            # Order management microservice
│   ├── src/
│   │   └── main/resources/
│   │       └── data.sql      # Seed data for testing
│   ├── Dockerfile
│   └── pom.xml
└── user-service/             # User management microservice
    ├── src/
    │   └── main/resources/
    │       └── data.sql      # Seed data for testing
    ├── Dockerfile
    └── pom.xml
```

---
## Getting Started

### Prerequisites
* Java 17 (or higher)
* Docker
* Docker Compose
* Apache Maven 3.9+

### How to Run
The entire application stack can be built and run with a single command from the root directory:
```bash
docker-compose up --build
```
This command will:
1.  Build the Docker images for all four microservices.
2.  Create and start the PostgreSQL database container.
3.  Start the Eureka Server, User Service, Order Service, and API Gateway.
4.  Start an Adminer container for easy database management.

---
## Accessing the Services
Once running, the services can be accessed at the following URLs:

* **API Gateway**: `http://localhost:8000` (Primary entry point for all API calls)
* **Eureka Dashboard**: `http://localhost:8761`
* **Adminer (DB GUI)**: `http://localhost:8085`

#### API Endpoints (via Gateway)
All API requests should go through the API Gateway on port `8000`.

**User Service (`/users/**`)**
* `GET http://localhost:8000/users`
* `GET http://localhost:8000/users/{id}`
* `POST http://localhost:8000/users`
* `PUT http://localhost:8000/users/{id}`
* `DELETE http://localhost:8000/users/{id}`

**Order Service (`/orders/**`)**
* `GET http://localhost:8000/orders`
* `GET http://localhost:8000/orders/{id}`
* `POST http://localhost:8000/orders`
* `PUT http://localhost:8000/orders/{id}`
* `DELETE http://localhost:8000/orders/{id}`

---
## CI/CD Pipeline
This project includes a `Jenkinsfile` that defines a full Continuous Integration pipeline. When a new commit is pushed to the `main` branch, the pipeline will automatically:
1.  Build all Docker images.
2.  Run the entire application stack using Docker Compose.
3.  Perform an end-to-end integration test.
4.  Clean up all running containers.
