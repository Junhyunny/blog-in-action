FROM gradle:jdk17 as builder

WORKDIR /build

COPY build.gradle settings.gradle /build/

RUN gradle build -x test --parallel --continue > /dev/null 2>&1 || true

COPY . /build

RUN gradle build -x test --parallel

# APP
FROM eclipse-temurin:17-jammy

WORKDIR /app

COPY --from=builder /build/build/libs/*-SNAPSHOT.jar ./app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]