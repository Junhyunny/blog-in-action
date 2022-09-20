docker run -d -p 8080:8080\
    --name volume-test\
    -e TODO_FILE_PATH=/app/volume/todos\
    -v todos:/app/volume\
    --rm\
    volume-test
