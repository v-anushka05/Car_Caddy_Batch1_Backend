# Use a Maven image with Java 17
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Set working directory
WORKDIR /app

# Copy the project files
COPY . .

# Ensure mvnw is executable
RUN chmod +x mvnw

# Build the project
RUN ./mvnw clean package -DskipTests

# Run stage (optional, adjust as needed)
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]