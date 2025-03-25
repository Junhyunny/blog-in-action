resource "aws_security_group" "junhyunny_ec2_sg" {
  vpc_id      = aws_vpc.junhyunny_vpc.id
  name        = "${var.project_name}-ec2-sg"
  description = "Security Group for EC2 Container"

  ingress {
    from_port = 8080
    to_port   = 8080
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

resource "aws_security_group" "junhyunny_alb_sg" {
  vpc_id      = aws_vpc.junhyunny_vpc.id
  name        = "${var.project_name}-alb-sg"
  description = "Security Group for Application Load Balancer"
  ingress {
    from_port = 80
    to_port   = 80
    protocol  = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
  ingress {
    from_port = 443
    to_port   = 443
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