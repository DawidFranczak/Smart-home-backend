FROM maven:3-eclipse-temurin-20-alpine AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn package -DskipTests

FROM openjdk:20-jdk-slim

WORKDIR /app
COPY --from=builder /app/target/smart_home-0.0.1.jar .
COPY src/main/resources/data.sql .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "smart_home-0.0.1.jar"]
