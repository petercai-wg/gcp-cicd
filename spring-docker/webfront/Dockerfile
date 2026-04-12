# Stage 1: Build the application with Maven
FROM maven:3.9.8-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install -U



FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Download the latest opentelemetry-javaagent.jar
RUN curl -L -O https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent.jar


COPY --from=build /app/target/*.jar app.jar



EXPOSE 8080


ENTRYPOINT ["java", "-javaagent:/app/opentelemetry-javaagent.jar", "-jar","app.jar"]
