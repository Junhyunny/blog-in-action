# 1
cd backend-maven
./mvnw clean compilejavsa

# 2
cd ../frontend
npm install && npm run build

# 3
cp -rf ./dist/ ../backend-maven/target/classes/static

# 4
cd ../backend-maven
./mvnw package
