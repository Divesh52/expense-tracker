# -----------------------------
# Stage 1: Build the Spring Boot app
# -----------------------------
FROM eclipse-temurin:17-jdk-jammy AS build

# Set working directory
WORKDIR /app

# Copy Maven wrapper and project files
COPY mvnw . 
COPY .mvn .mvn
COPY pom.xml .
COPY src src

# Make Maven wrapper executable
RUN chmod +x mvnw

# Build the Spring Boot app (skip tests for faster build)
RUN ./mvnw clean package -DskipTests

# -----------------------------
# Stage 2: Run the Spring Boot app
# -----------------------------
FROM eclipse-temurin:17-jdk-jammy

# Set working directory
WORKDIR /app

# Copy the JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port 8080 (Render maps $PORT automatically)
EXPOSE 8080

# Run the JAR (Spring Boot will read database config from env variables)
CMD ["sh", "-c", "java -jar app.jar"]
