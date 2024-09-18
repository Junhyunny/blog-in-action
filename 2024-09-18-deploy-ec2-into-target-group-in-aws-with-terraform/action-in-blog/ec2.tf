resource "aws_instance" "web" {
  ami                         = "ami-066784287e358dad1"
  instance_type               = "t2.micro"
  subnet_id                   = aws_subnet.public_subnet_1.id
  associate_public_ip_address = true
  vpc_security_group_ids = [aws_security_group.junhyunny_alb_web_sg.id]
  depends_on = [aws_instance.database]
  user_data                   = <<-EOF
    #!/bin/bash
    set -ex
    sudo yum update -y
    sudo yum install docker -y
    sudo service docker start
    sudo usermod -a -G docker ec2-user
    docker run -p 8080:8080 -d\
      -e ACTIVE_PROFILE=aws\
      -e DATABASE_URL=jdbc:postgresql://${aws_instance.database.private_ip}:5432/postgres\
      -e DATABASE_DRIVER_CLASS=org.postgresql.Driver\
      -e DATABASE_USERNAME=postgres\
      -e DATABASE_PASSWORD=12345\
      --restart unless-stopped\
      opop3966/todo-list
  EOF
  tags = {
    Name = "${var.project_name}-web-application"
  }
}

resource "aws_instance" "database" {
  ami           = "ami-066784287e358dad1"
  instance_type = "t2.micro"
  subnet_id     = aws_subnet.private_subnet_1.id
  vpc_security_group_ids = [aws_security_group.junhyunny_web_database_sg.id]
  user_data     = <<-EOF
    #!/bin/bash
    set -ex
    sudo yum update -y
    sudo yum install docker -y
    sudo service docker start
    sudo usermod -a -G docker ec2-user
    docker run -p 5432:5432 -d -e POSTGRES_PASSWORD=12345 postgres
  EOF
  tags = {
    Name = "${var.project_name}-database"
  }
}
