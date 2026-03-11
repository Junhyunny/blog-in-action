resource "aws_dynamodb_table" "todos_table" {
  name     = "${var.environment}-todos-table"
  hash_key = "id"
  attribute {
    name = "id"
    type = "N"
  }
  billing_mode = "PAY_PER_REQUEST"
}
