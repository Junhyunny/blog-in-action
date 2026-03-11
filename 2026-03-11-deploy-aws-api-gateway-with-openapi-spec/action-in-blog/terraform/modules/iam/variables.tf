variable "environment" {
  type        = string
  default     = "dev"
  description = "Deployment environment (dev, stage, prod)"
}

variable "dynamodb_table_arn" {
  type        = string
  default     = ""
  description = "ARN of the DynamoDB table to which the IAM role will have access. If empty, no DynamoDB permissions will be granted."
}
