variable "environment" {
  type        = string
  default     = "dev"
  description = "deployment environment (dev, stage, prod)"
}

variable "region" {
  type        = string
  default     = "ap-northeast-2"
  description = "AWS region for Lambda functions"
}

variable "lambda_functions" {
  type        = list(string)
  default     = []
  description = "lambda function names to be created"
}

variable "lambda_roles" {
  type        = map(string)
  default     = {}
  description = "IAM role ARNs for each lambda function"
  validation {
    condition     = alltrue([for v in values(var.lambda_roles) : can(regex("^$|^arn:", v))])
    error_message = "Each value in lambda_roles must be either an empty string or a valid"
  }
}
