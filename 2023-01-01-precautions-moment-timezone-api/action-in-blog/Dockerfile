FROM node:16-alpine3.15 as YARN_TEST

WORKDIR /app

COPY package.json ./package.json

RUN npm install

COPY babel.config.js .

COPY *.test.ts .

CMD ["npm", "test", "--coverage"]