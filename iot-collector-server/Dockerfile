#
# Build stage
#
FROM maven:3.9.9-eclipse-temurin-17 AS build-stage
WORKDIR /usr/src/app
COPY . ./
RUN mvn clean package

#
# Package stage
#
FROM openjdk:17-jdk-slim AS final-stage
WORKDIR /usr/src/app
EXPOSE 9090
COPY --from=build-stage /usr/src/app/target/*.jar /usr/src/app/app.jar
ENTRYPOINT ["java","-jar","/usr/src/app/app.jar"]