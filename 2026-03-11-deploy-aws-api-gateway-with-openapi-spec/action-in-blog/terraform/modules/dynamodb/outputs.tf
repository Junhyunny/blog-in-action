output "table_arn" {
  description = "DynamoDB todos 테이블 ARN"
  value       = aws_dynamodb_table.todos_table.arn
}

