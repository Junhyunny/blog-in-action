resource "aws_instance" "server" {
  ami           = "ami-0a9e614c3d1eaa27d"
  instance_type = "t4g.large"
  subnet_id     = aws_subnet.private_subnet_1.id
  vpc_security_group_ids = [aws_security_group.junhyunny_ec2_sg.id]
  user_data     = <<-EOF
    #!/bin/bash
    set -ex
    sudo yum update -y
    sudo yum install docker -y
    sudo service docker start
    sudo usermod -a -G docker ec2-user
    docker run -p 8080:8080 -d\
      -e GOOGLE_CLIENT_ID=${var.google_client_id}\
      -e GOOGLE_CLIENT_SECRET=${var.google_client_secret}\
      --restart unless-stopped\
      junhyunny/base-url
  EOF
  tags = {
    Name = "${var.project_name}-server-application"
  }
}

resource "aws_lb_target_group_attachment" "target-group-web-attach-resource" {
  target_group_arn = aws_alb_target_group.junhyunny_alb_target_group.arn
  target_id        = aws_instance.server.id
  port             = 8080
  depends_on = [aws_lb_listener.junhyunny_http_alb_listener, aws_lb_listener.junhyunny_https_alb_listener]
}
