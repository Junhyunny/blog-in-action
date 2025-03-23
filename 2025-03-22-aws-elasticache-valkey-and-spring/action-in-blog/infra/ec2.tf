resource "aws_instance" "server" {
  ami                         = "ami-0a9e614c3d1eaa27d"
  instance_type               = "t4g.large"
  subnet_id                   = aws_subnet.public_subnet_1.id
  associate_public_ip_address = true
  vpc_security_group_ids = [aws_security_group.junhyunny_ec2_sg.id]
  user_data                   = <<-EOF
    #!/bin/bash
    set -ex
    sudo yum update -y
    sudo yum install docker -y
    sudo service docker start
    sudo usermod -a -G docker ec2-user
    docker run -p 8080:8080 -d\
      -e SPRING_PROFILES_ACTIVE=cloud\
      -e SESSION_HOST=${aws_elasticache_serverless_cache.valkey-cluster.endpoint[0].address}:${aws_elasticache_serverless_cache.valkey-cluster.endpoint[0].port}\
      --restart unless-stopped\
      junhyunny/todo-list
  EOF
  tags = {
    Name = "${var.project_name}-server-application"
  }
}

output "server_endpoint" {
  value       = aws_instance.server.public_ip
  description = "EC2 엔드포인트"
}
