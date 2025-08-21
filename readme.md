**Spring Boot Microservices with Docker and PostgreSQL**
This project demonstrates a simple microservice architecture using Spring Boot, Docker, and PostgreSQL. It consists of three independent services working together, orchestrated by an API Gateway:

API Gateway: The single entry point for all client requests, responsible for routing, load balancing, and potentially other cross-cutting concerns like authentication.

Eureka Server: The service registry and discovery server for the microservices.

User Service: Manages user-related operations, including a full RESTful CRUD API.

Order Service: Manages order-related operations and communicates with the User Service to fetch user details.

Each service is a standalone Spring Boot application, containerized using Docker, and connects to its own dedicated PostgreSQL database.

- **User Service**: Manages user-related operations.
  eureka discover client(spring cloud discovery)
  spring data jpa
  spring web
  posgtrs driver
  springboot dev tools
  lombok
- **Order Service**: Manages order-related operations and may communicate with the User Service.
  eureka discover client(spring cloud discovery)
  spring data jpa
  spring web
  posgtrs driver
  springboot dev tools
  lombok
- **eureka Service**:
  spring cloud eureka server
  spring boot dev tools
- **api-gateway  server:**
  spring cloud gateway
  spring cloud netflic eureka client

Each service:
- Is a standalone Spring Boot application.
- Will connect to its own PostgreSQL database.
- Will be containerized using Docker.

---

##  Project Structure
spring-boot/
├── docker-compose.yml           # To define and run multi-container Docker apps
├── api-gateway/                 # The single entry point for all client requests
│   ├── src/
│   │   └── main/
│   │       ├── java/com/spring_boot/api_gateway/
│   │       │   ├── ApiGatewayApplication.java
│   │       └── resources/
│   │           └── application.yml
│   └── pom.xml
├── eureka-server/               # The service registry for discovery
│   ├── src/
│   │   └── main/
│   │       ├── java/com/spring_boot/eureka_server/
│   │       │   └── EurekaServerApplication.java
│   │       └── resources/
│   │           └── application.properties
│   └── pom.xml
├── order-service/               # Handles all order-related operations
│   ├── src/
│   │   └── main/
│   │       ├── java/com/spring_boot/order_service/
│   │       │   ├── controller/      # REST endpoints for orders
│   │       │   ├── dto/             # Data transfer objects
│   │       │   ├── entity/          # JPA entities for orders
│   │       │   ├── exception/       # Custom exceptions
│   │       │   └── repository/      # Spring Data JPA repositories
│   │       └── resources/
│   │           ├── application.properties
│   │           └── init.sql
│   └── pom.xml
└── user-service/                # Handles all user-related operations
    ├── src/
    │   └── main/
    │       ├── java/com/spring_boot/user_service/
    │       │   ├── controller/      # REST endpoints for users
    │       │   ├── entity/          # JPA entities for users
    │       │   ├── exception/       # Custom exceptions
    │       │   └── repository/      # Spring Data JPA repositories
    │       └── resources/
    │           ├── application.properties
    │           └── init.sql
    └── pom.xml


**Core Components and Features**
API Gateway: Provides a unified API interface for external clients. It intelligently routes incoming requests to the correct microservice using service discovery.

Service Discovery: Utilizes Spring Cloud Eureka Server to enable dynamic service registration and discovery, allowing services to find each other without hardcoding URLs.

RESTful APIs: The User and Order services expose REST endpoints for CRUD (Create, Read, Update, Delete) operations.

Database: Each microservice is backed by its own dedicated PostgreSQL database.

Containerization: All services and databases are containerized using Docker for isolation and easy deployment.

Orchestration: docker-compose simplifies the management of the multi-container application.

Development Workflow: Docker Compose is configured with volume synchronization (sync+restart and rebuild) for a seamless development experience with hot-reloading.

Global Exception Handling: A centralized exception handler provides consistent JSON error responses for ResourceNotFoundException.

API Documentation: API endpoints are automatically documented via Springdoc and are accessible via Swagger UI.

###  Start with Docker Compose

docker-compose up --build
This command will:
  - Build the Docker images for all services.
  - Create and start the PostgreSQL database containers.
  - Start the Eureka Server, which will run the discovery server.
  - Start the User, Order, and API Gateway services, which will register themselves with the Eureka server.
  - Start Adminer for database management

**Accessing the Services**
The primary entry point is now the API Gateway. It will route requests to the other services.
- API Gateway: http://localhost:8000
- Eureka Dashboard: http://localhost:8761
- User Service: http://localhost:8081 (Internal, for debugging only)
- Order Service: http://localhost:8082 (Internal, for debugging only)
- Adminer: http://localhost:8085 (Connect to postgres_user_db or - - - postgres_order_db using postgres for both user and password).

**API Endpoints via the API Gateway**

The following are the new, single-entry point URLs to access your microservices. The API Gateway will handle the routing internally.

User Service (via Gateway):
- GET http://localhost:8000/users
- GET http://localhost:8000/users/{id}
- POST http://localhost:8000/users
- PUT http://localhost:8000/users/{id}
- DELETE http://localhost:8000/users/{id}

Order Service (via Gateway):
- GET http://localhost:8000/orders
- POST http://localhost:8000/orders
- PUT http://localhost:8000/orders/{id}
- DELETE http://localhost:8000/orders/{id}

##  Progress

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
  - Ports: maps container's 5432 to host's 5435
  - Volume for data persistence and running initialization SQL scripts from `./docker/postgres:/docker-entrypoint-initdb.d `

- **Adminer service:**
  - Image: `adminer`
  - Port: 8085 (host) → 8080 (container)
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

- **order service:**
  - Build context: ./order-service
  - Ports: 8082:8082
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
-docker/postgres/init.sql
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

- **create discovery server:**
-an eureka server projetc
-dependencies 
  spring cloud erueka server
  spring boot dev tools
  
- **create apigateway  server:**
  spring cloud gateway
  spring cloud netflic eureka client