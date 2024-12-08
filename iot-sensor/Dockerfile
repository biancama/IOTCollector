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
ENV SERIAL_VALUE serial1
ENV TYPE TEMP
ENV TOPIC temperature.sensor1
ENV PERIOD 10000
ENV HOSTNAME host.docker.internal
COPY --from=build-stage /usr/src/app/target/*-fat.jar /usr/src/app/app.jar
ENTRYPOINT sh -c 'java -jar /usr/src/app/app.jar "$SERIAL_VALUE" "$TOPIC" -t "$TYPE" -f "$PERIOD" -s "$HOSTNAME"'