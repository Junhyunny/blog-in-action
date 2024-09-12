resource "aws_route_table" "rtb_public" {
  vpc_id = aws_vpc.junhyunny_vpc.id
  route {
    cidr_block = "10.0.0.0/16"
    gateway_id = "local"
  }
  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.igw.id
  }
  tags = {
    Name = "${var.project_name}-rtb-public-1"
  }
}

resource "aws_route_table" "rtb_private" {
  vpc_id = aws_vpc.junhyunny_vpc.id
  route {
    cidr_block = "10.0.0.0/16"
    gateway_id = "local"
  }
  route {
    cidr_block     = "0.0.0.0/0"
    nat_gateway_id = aws_nat_gateway.nat_gateway.id
  }
  tags = {
    Name = "${var.project_name}-rtb-private-1"
  }
}

resource "aws_route_table_association" "demo-routing-public" {
  count          = 2
  subnet_id = element([aws_subnet.public_subnet_1.id, aws_subnet.public_subnet_2.id], count.index)
  route_table_id = aws_route_table.rtb_public.id
}

resource "aws_route_table_association" "demo-routing-private-1" {
  subnet_id      = aws_subnet.private_subnet_1.id
  route_table_id = aws_route_table.rtb_private.id
}
