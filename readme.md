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
├──user-service/
  ├── src/
  │ └── main/
  │ ├── java/com/spring_boot/user_service/
  │ │ ├── controller/ # REST controllers
  │ │ ├── entity/ # JPA entities
  │ │ ├── exception/ # Custom exceptions
  │ │ └── repository/ # Spring Data JPA Repositories
  │ └── resources/
  │   └── application.properties
  │   └── init.sql
  ├── pom.xml
└── order-service/ # Spring Boot service for order management

###  Start with Docker Compose

docker-compose up --build
This will:
Start PostgreSQL on port 5435
Start Adminer on port 8082
Start the user-service on port 8081

##  Current Progress

1. **Created folder structure** for microservice architecture.
2. **Initialized two Spring Boot projects** using Spring Initializr:
    - `user-service` and `order-service`
3. Each project includes:
    - Java package structure
    - `application.properties`
    - `pom.xml` with necessary dependencies
4. Created a top-level `docker-compose.yml` file (to be filled later) for managing containers.
5. Docker Compose Setup
### docker-compose.yml

- **Postgres service:**
  - Image: `postgres:latest`
  - Environment variables:
    - `POSTGRES_USER=postgres`
    - `POSTGRES_PASSWORD=postgres`
    - `POSTGRES_DB=user_db` (creates the database on startup)
  - Ports: maps container's 5432 to host's 5435
  - Volume for data persistence and running initialization SQL scripts from `./db-init`

- **Adminer service:**
  - Image: `adminer`
  - Port: 8082 (host) → 8080 (container)
  - Depends on the Postgres container

- **user service:**
  - Build context: ./user-service
  - Ports: 8081:8081
  - Volume Sync (Watch Support):
      develop:
        watch:
          - path: ./user-service/src
            target: /app/src
            action: sync+restart
          - path: ./user-service/pom.xml
            target: /app/pom.xml
            action: rebuild
---

6. Database Initialization

-Spring Boot automatically manages database schema creation and updates using JPA and Hibernate.
 
 7. Spring Boot Core Implementation
 Entity class User created with fields: id, name, email
 Repository UserRepository (extends JpaRepository)
 Controller UserController with endpoints:
        GET /api/users – Get all users
        GET /api/users/{id} – Get user by ID
        POST /api/users – Create new user
        PUT /api/users/{id} – Update user by ID
        DELETE /api/users/{id} – Delete user by ID
      
8. Global Exception Handling
 ResourceNotFoundException class implemented
 GlobalExceptionHandler to handle all exceptions and return JSON responses with:
        Timestamp
        Message
        Details

9. API Documentation
 Swagger UI via Springdoc available at http://localhost:8081/swagger-ui.html