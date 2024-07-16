# 1
cd backend-gradle
./gradlew clean build

# 2
cd ../frontend
npm install && npm run build

# 3
cp -rf ./dist/ ../backend-gradle/build/resources/main/static

# 4
cd ../backend-gradle
./gradlew bootJar
