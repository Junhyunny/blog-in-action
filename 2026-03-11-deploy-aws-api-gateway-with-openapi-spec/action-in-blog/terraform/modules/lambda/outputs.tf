output "invoke_arns" {
  description = "Lambda 함수별 invoke ARN 맵 (key: 함수명, value: invoke_arn)"
  value = {
    for k, fn in aws_lambda_function.functions : k => fn.invoke_arn
  }
}

