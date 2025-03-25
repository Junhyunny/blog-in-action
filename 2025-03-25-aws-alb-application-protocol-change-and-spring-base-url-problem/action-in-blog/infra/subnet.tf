resource "aws_subnet" "public_subnet_1" {
  vpc_id            = aws_vpc.junhyunny_vpc.id
  cidr_block        = "10.0.0.0/20"
  availability_zone = "${var.region}a"
  tags = {
    Name = "${var.vpc_name}-public-subnet-1"
  }
}

resource "aws_subnet" "private_subnet_1" {
  vpc_id                  = aws_vpc.junhyunny_vpc.id
  cidr_block              = "10.0.16.0/20"
  availability_zone       = "${var.region}a"
  map_public_ip_on_launch = false
  tags = {
    Name = "${var.vpc_name}-private-subnet-1"
  }
}

resource "aws_subnet" "public_subnet_2" {
  vpc_id                  = aws_vpc.junhyunny_vpc.id
  cidr_block              = "10.0.32.0/20"
  availability_zone       = "${var.region}c"
  tags = {
    Name = "${var.vpc_name}-public-subnet-2"
  }
}