resource "aws_internet_gateway" "igw" {
  vpc_id = aws_vpc.junhyunny_vpc.id
  tags = {
    Name = "${var.project_name}-igw"
  }
}

resource "aws_eip" "nat" {}

resource "aws_nat_gateway" "nat_gateway" {
  allocation_id = aws_eip.nat.id
  subnet_id     = aws_subnet.public_subnet_1.id
  tags = {
    Name = "${var.project_name}-nat-gateway"
  }
}