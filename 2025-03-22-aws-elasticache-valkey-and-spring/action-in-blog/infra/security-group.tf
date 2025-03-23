resource "aws_security_group" "junhyunny_ec2_sg" {
  vpc_id      = aws_vpc.junhyunny_vpc.id
  name        = "${var.project_name}-ec2-sg"
  description = "Security Group for EC2 Container"
  ingress {
    from_port = 8080
    to_port   = 8080
    protocol  = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
  egress {
    from_port = 0
    to_port   = 0
    protocol  = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_security_group" "junhyunny_cache_cluster_sg" {
  vpc_id      = aws_vpc.junhyunny_vpc.id
  name        = "${var.project_name}-cache-cluster-sg"
  description = "Security Group for ElastiCache Cluster"
  ingress {
    from_port = 6379
    to_port   = 6379
    protocol  = "tcp"
    cidr_blocks = ["10.0.0.0/16"]
  }
  ingress {
    from_port = 6380
    to_port   = 6380
    protocol  = "tcp"
    cidr_blocks = ["10.0.0.0/16"]
  }
  egress {
    from_port = 0
    to_port   = 0
    protocol  = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}