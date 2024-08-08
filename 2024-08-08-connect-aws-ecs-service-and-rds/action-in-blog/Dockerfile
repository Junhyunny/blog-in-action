# 1. build frontend
FROM node:18.20.4-alpine AS frontend-build
WORKDIR /app
COPY frontend/package.json ./
RUN npm install
COPY frontend ./
RUN npm run build

# 2. build backend
FROM gradle:7.6.0-jdk17 AS backend-build
WORKDIR /app
ENV TZ="Asia/Seoul"
COPY backend/gradlew gradlew
COPY backend/gradle gradle
COPY backend/build.gradle.kts build.gradle.kts
COPY backend/settings.gradle.kts settings.gradle.kts
RUN ./gradlew build --no-daemon || return 0
COPY backend/src src
COPY --from=frontend-build /app/dist src/main/resources/static
RUN ./gradlew build -i

# 3. make running container image
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=backend-build /app/build/libs/demo-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
