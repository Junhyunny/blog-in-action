docker run -p 3306:3306\
    --name mysql\
    -e MYSQL_ROOT_PASSWORD=123\
    -d mysql