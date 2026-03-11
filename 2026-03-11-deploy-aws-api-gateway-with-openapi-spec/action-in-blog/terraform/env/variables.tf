variable "region" {
  type        = string
  default     = "ap-northeast-1"
  description = "region name"
}

variable "environment" {
  type        = string
  default     = "dev"
  description = "deployment environment (dev, stage, prod)"
}

variable "lambda_functions" {
  type = list(string)
  default = [
    "DeleteTodoFunction",
    "GetTodoByIdFunction",
    "GetTodosFunction",
    "PostTodoFunction",
    "PutTodoFunction",
  ]
  description = "lambda function names to be created"
}
