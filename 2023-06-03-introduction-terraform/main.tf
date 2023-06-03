variable "AWS_ACCESS_KEY" {
  default = "your access key"
}
variable "AWS_SECRET_KEY" {
  default = "your secret key"
}
variable "AWS_SESSION_TOKEN" {
  default = "your session token"
}

provider "aws" {
  access_key = var.AWS_ACCESS_KEY
  secret_key = var.AWS_SECRET_KEY
  token      = var.AWS_SESSION_TOKEN
  region     = "us-east-1"
}

resource "aws_instance" "example" {
  ami = "ami-13be557e"
  instance_type = "t2.micro"

  tags = {
    Name = "example-ec2"
  }
}

output "new-instance-public-ip" {
    value = aws_instance.example.public_ip
}