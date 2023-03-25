FROM node:16-buster-slim as builder

WORKDIR /app

COPY package.json .

RUN npm install --silent

COPY . .

RUN npm run build

FROM nginx

COPY default.conf /etc/nginx/conf.d/default.conf

COPY ssl /ssl

COPY --from=builder /app/build /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
