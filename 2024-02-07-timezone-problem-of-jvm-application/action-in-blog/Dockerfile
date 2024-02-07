FROM gradle:7.4-jdk17-alpine as builder

WORKDIR /build

COPY build.gradle settings.gradle /build/

RUN gradle build -x test --parallel --continue > /dev/null 2>&1 || true

COPY . /build

RUN gradle build -x test --parallel

FROM openjdk:17.0-slim

WORKDIR /app

COPY --from=builder /build/build/libs/*.jar ./app.jar

EXPOSE 8080

ENTRYPOINT java -jar app.jar
