docker run -p 5432:5432\
    --name postgres\
    -e POSTGRES_PASSWORD="${POSTGRES_PASSWORD:?Set POSTGRES_PASSWORD}"\
    -e TZ=Asia/Seoul\
    -d postgres
