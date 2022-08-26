FROM node:16-buster-slim as builder

WORKDIR /app

COPY package.json .

RUN npm isntall --slient

COPY . .

RUN npm run build

FROM nginx

COPY conf/nginx.conf /etc/nginx/conf.d/default.conf

COPY --from=builder /app/dist /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]