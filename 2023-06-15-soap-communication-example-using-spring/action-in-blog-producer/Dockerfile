FROM gradle:jdk17 as build

WORKDIR /app

COPY settings.gradle gradlew ./

COPY gradle ./gradle

COPY build.gradle ./

COPY src ./src

RUN ./gradlew clean build

FROM openjdk:17-alpine

WORKDIR /app

COPY --from=build /app/build/libs/*.jar ./app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "./app.jar"]