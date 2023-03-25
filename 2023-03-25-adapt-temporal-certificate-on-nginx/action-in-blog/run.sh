docker build -t nginx-ssl .

docker run -d\
  -p 80:80\
  -p 443:443\
  --name nginx-ssl\
  nginx-ssl