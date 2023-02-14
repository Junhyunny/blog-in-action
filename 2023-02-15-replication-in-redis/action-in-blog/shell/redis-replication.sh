echo '' > ../conf/redis-master.conf
cp  ../conf/redis.conf ../conf/redis-slave-1.conf
cp  ../conf/redis.conf ../conf/redis-slave-2.conf
docker-compose -f ../docker-compose-replication.yml down
docker-compose -f ../docker-compose-replication.yml build
docker-compose -f ../docker-compose-replication.yml up