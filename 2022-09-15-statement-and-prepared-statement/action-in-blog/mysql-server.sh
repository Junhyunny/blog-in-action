docker run -p 3306:3306\
    --name mysql\
    -e MYSQL_ROOT_PASSWORD="${MYSQL_ROOT_PASSWORD:?Set MYSQL_ROOT_PASSWORD}"\
    -d mysql
