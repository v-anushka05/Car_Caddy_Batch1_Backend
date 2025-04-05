# Use the official Maven image to build the project
FROM maven:3.8.5-openjdk-17 AS build

# Set working directory inside the container
WORKDIR /app

# Copy all files to the container
COPY . .

# Package the application using Maven
RUN mvn clean package -DskipTests

# Use a minimal JDK runtime image for production
FROM openjdk:17-jdk-slim

# Set working directory for the runtime container
WORKDIR /app

# Copy the packaged jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
