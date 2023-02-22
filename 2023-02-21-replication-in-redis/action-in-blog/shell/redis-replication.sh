echo 'port 6379' > conf/redis-master.conf
cp  conf/redis.conf conf/redis-slave-1.conf
cp  conf/redis.conf conf/redis-slave-2.conf
docker-compose down
docker-compose build
docker-compose up