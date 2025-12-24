# Stage 1: Build the application
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy the Maven project files
COPY pom.xml .
COPY .mvn ./.mvn

# Copy the source code
COPY src ./src

# Build the application, skipping tests as they are run in CI
RUN mvn clean package -DskipTests

# Stage 2: Create the final, minimal image
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Create a non-root user and group
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Copy the JAR from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Switch to the non-root user
USER appuser

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
