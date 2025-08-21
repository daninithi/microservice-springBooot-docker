# ==========================
# Stage 1: Build the Spring Boot App using Maven
# ==========================
# Use the official Maven image with JDK 17 for building the application
FROM maven:3.9.6-eclipse-temurin-17 AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and the project source code
COPY ./pom.xml ./pom.xml
COPY ./src ./src

# Build the Spring Boot application and skip running tests to save time
RUN mvn clean package -DskipTests

# ==========================
# Stage 2: Run the App with a lightweight JDK
# ==========================
# Use a lightweight JDK 17 JRE image for running the built application
FROM eclipse-temurin:17-jdk

# Set the working directory inside the runtime container
WORKDIR /app

# Copy the JAR file built in the first stage into the runtime container
COPY --from=builder /app/target/*.jar app.jar

# Expose the port the application will run on
EXPOSE 8000

# Define the default command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]