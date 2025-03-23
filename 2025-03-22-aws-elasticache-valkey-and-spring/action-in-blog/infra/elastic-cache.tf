resource "aws_elasticache_subnet_group" "valkey_subnet_group" {
  name = "valkey-subnet-group"
  subnet_ids = [aws_subnet.private_subnet_1.id]
}

resource "aws_elasticache_serverless_cache" "valkey-cluster" {
  name                 = "valkey-cluster"
  engine               = "valkey"
  major_engine_version = "8"
  security_group_ids = [aws_security_group.junhyunny_cache_cluster_sg.id]
  subnet_ids    = [aws_subnet.private_subnet_1.id, aws_subnet.private_subnet_2.id]
}

output "valkey_endpoint" {
  value       = aws_elasticache_serverless_cache.valkey-cluster.endpoint
  description = "ElastiCache Valkey 클러스터 엔드포인트"
}
