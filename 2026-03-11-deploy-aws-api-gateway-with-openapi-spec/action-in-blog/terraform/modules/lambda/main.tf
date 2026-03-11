locals {
  lambda_function_keys = { for fname in var.lambda_functions : fname => fname }
}

data "archive_file" "lambda" {
  for_each    = local.lambda_function_keys
  type        = "zip"
  source_file = "${path.module}/../../../lambda/.aws-sam/build/${each.key}/app.js"
  output_path = "${path.module}/dist/${each.key}.zip"
}

resource "aws_lambda_function" "functions" {
  for_each = local.lambda_function_keys

  function_name                  = "${var.environment}-${each.key}"
  runtime                        = "nodejs22.x"
  handler                        = "app.lambdaHandler"
  timeout                        = 60
  memory_size                    = 256
  role                           = lookup(var.lambda_roles, each.key, "")
  filename                       = data.archive_file.lambda[each.key].output_path
  source_code_hash               = data.archive_file.lambda[each.key].output_base64sha256
  reserved_concurrent_executions = 10

  environment {
    variables = {
      REGION     = var.region
      TABLE_NAME = "${var.environment}-todos-table"
    }
  }
}
