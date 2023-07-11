APPLICATION="ROOT.war"

cd backend
./gradlew clean build

cd ../frontend
npm install
npm run build
cp -rf ./build/ ../backend/build/resources/main/static

cd ../backend
./gradlew bootWar
mv build/libs/backend-0.0.1-SNAPSHOT.war build/libs/$APPLICATION
mv build/libs/$APPLICATION ~/Desktop/apache-tomcat-10.1.11/webapps
