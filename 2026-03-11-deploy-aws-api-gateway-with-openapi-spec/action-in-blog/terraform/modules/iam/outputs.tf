output "lambda_role" {
  description = "Lambda 함수별 IAM Role ARN 맵"
  value = {
    DeleteTodoFunction  = aws_iam_role.lambda_exec.arn
    GetTodoByIdFunction = aws_iam_role.lambda_exec.arn
    GetTodosFunction    = aws_iam_role.lambda_exec.arn
    PostTodoFunction    = aws_iam_role.lambda_exec.arn
    PutTodoFunction     = aws_iam_role.lambda_exec.arn
  }
}