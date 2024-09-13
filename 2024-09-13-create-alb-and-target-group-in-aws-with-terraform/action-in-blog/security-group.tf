resource "aws_security_group" "junhyunny_alb_sg" {
  vpc_id      = aws_vpc.junhyunny_vpc.id
  description = "${var.project_name}-alb-sg"
  name        = "${var.project_name}-alb-sg"
  ingress {
    from_port = 80
    to_port   = 80
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
