variable "environment" {
  type        = string
  default     = "dev"
  description = "deployment environment (dev, stage, prod)"
}

variable "lambda_invoke_arns" {
  type        = map(string)
  default     = {}
  description = "Lambda 함수별 invoke ARN 맵 (key: 함수명, value: invoke_arn)"
}