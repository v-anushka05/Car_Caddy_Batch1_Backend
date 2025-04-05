# Build stage
FROM openjdk:8-jdk-alpine AS build

# Install bash and make mvnw executable
RUN apk add --no-cache bash
WORKDIR /app
COPY . .
RUN chmod +x mvnw  # Ensure mvnw is executable
RUN ./mvnw clean package -DskipTests

# Run stage
FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAVA_OPTS
ENV JAVA_OPTS=$JAVA_OPTS
COPY --from=build /app/target/CarManagement-0.0.1-SNAPSHOT.jar backend.jar
EXPOSE ${PORT:-8080}
ENTRYPOINT exec java $JAVA_OPTS -jar backend.jar --server.port=${PORT:-8080} --server.address=0.0.0.0