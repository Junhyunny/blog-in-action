FROM maven:3.8.6-jdk-11 as MAVEN_BUILD

WORKDIR /build

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src ./src

RUN mvn package -Dmaven.test.skip=true

FROM openjdk:11-jdk-slim-buster

WORKDIR /app

VOLUME /app/volume

ARG JAR_FILE=*.jar

ENV TODO_FILE_PATH /app/volume/todos

COPY --from=MAVEN_BUILD /build/target/${JAR_FILE} ./app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]