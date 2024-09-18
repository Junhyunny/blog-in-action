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

resource "aws_security_group" "junhyunny_alb_web_sg" {
  vpc_id      = aws_vpc.junhyunny_vpc.id
  name        = "${var.project_name}-alb-web-sg"
  description = "SG for ${var.project_name} from ALB to WEB EC2 Container"
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

resource "aws_security_group" "junhyunny_web_database_sg" {
  vpc_id      = aws_vpc.junhyunny_vpc.id
  name        = "${var.project_name}-web-database-sg"
  description = "SG for ${var.project_name} from WEB EC2 Container to database"
  ingress {
    from_port = 5432
    to_port   = 5432
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