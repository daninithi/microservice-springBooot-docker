# Spring Boot Microservices with Docker and PostgreSQL

This project demonstrates a simple microservice architecture using Spring Boot, Docker, and PostgreSQL. It consists of two independent microservices:

- **User Service**: Manages user-related operations.
- **Order Service**: Manages order-related operations and may communicate with the User Service.

Each service:
- Is a standalone Spring Boot application.
- Will connect to its own PostgreSQL database.
- Will be containerized using Docker.

---

##  Project Structure
spring-boot/
├── docker-compose.yml # To define and run multi-container Docker apps
├── user-service/ # Spring Boot service for user management
└── order-service/ # Spring Boot service for order management

##  Current Progress

1. **Created folder structure** for microservice architecture.
2. **Initialized two Spring Boot projects** using Spring Initializr:
    - `user-service` and `order-service`
3. Each project includes:
    - Java package structure
    - `application.properties`
    - `pom.xml` with necessary dependencies
4. Created a top-level `docker-compose.yml` file (to be filled later) for managing containers.
