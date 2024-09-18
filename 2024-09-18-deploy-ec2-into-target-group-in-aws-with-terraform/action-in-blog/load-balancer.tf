resource "aws_lb" "junhyunny_alb" {
  internal           = false
  load_balancer_type = "application"
  security_groups = [aws_security_group.junhyunny_alb_sg.id]
  subnets = [aws_subnet.public_subnet_1.id, aws_subnet.public_subnet_2.id]
  tags = {
    Name = "${var.project_name}-alb"
  }
}

resource "aws_alb_target_group" "junhyunny_alb_target_group" {
  name        = "${var.project_name}-alb-target-group"
  port        = "8080"
  protocol    = "HTTP"
  vpc_id      = aws_vpc.junhyunny_vpc.id
  target_type = "instance"

  health_check {
    path     = "/"
    protocol = "HTTP"
    interval = 30
    timeout  = 5
  }
}

resource "aws_lb_listener" "junhyunny_alb_listener" {
  load_balancer_arn = aws_lb.junhyunny_alb.arn
  port              = "80"
  protocol          = "HTTP"
  default_action {
    type             = "forward"
    target_group_arn = aws_alb_target_group.junhyunny_alb_target_group.arn
  }
}

resource "aws_lb_target_group_attachment" "target-group-web-attach-resource" {
  target_group_arn = aws_alb_target_group.junhyunny_alb_target_group.arn
  target_id        = aws_instance.web.id
  port             = 8080
  depends_on = [aws_lb_listener.junhyunny_alb_listener]
}

output "alb-dns" {
  value = aws_lb.junhyunny_alb.dns_name
}