docker build . -t nginx-compression
docker stop nginx-compression && docker rm nginx-compression
docker run -d -p 80:80 --name nginx-compression nginx-compression