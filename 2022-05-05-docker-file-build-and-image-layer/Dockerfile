FROM node:12

WORKDIR /appA

COPY ./action-in-blog /app

RUN npm install

EXPOSE 80

CMD ["node", "server.js"]
