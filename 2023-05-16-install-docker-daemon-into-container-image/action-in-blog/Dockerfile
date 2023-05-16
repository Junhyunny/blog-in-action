FROM maven:3-eclipse-temurin-17

USER root

RUN apt-get update

RUN apt-get install -y init systemd docker.io

WORKDIR /build

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src ./src

ENTRYPOINT ["/sbin/init"]