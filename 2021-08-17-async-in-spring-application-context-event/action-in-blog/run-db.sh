#!/bin/bash

docker run -d \
  --name mysql-test \
  -p 3306:3306 \
  -e MYSQL_ROOT_PASSWORD="${MYSQL_ROOT_PASSWORD:?Set MYSQL_ROOT_PASSWORD}" \
  -e MYSQL_DATABASE=test \
  mysql:8.0
