locals {
  openapi_spec_path = "${path.module}/../../../api/openapi-specification.yaml"

  openapi_spec = templatefile(local.openapi_spec_path, {
    invoke_arn_get_todos   = lookup(var.lambda_invoke_arns, "GetTodosFunction", "")
    invoke_arn_post_todo   = lookup(var.lambda_invoke_arns, "PostTodoFunction", "")
    invoke_arn_get_by_id   = lookup(var.lambda_invoke_arns, "GetTodoByIdFunction", "")
    invoke_arn_put_todo    = lookup(var.lambda_invoke_arns, "PutTodoFunction", "")
    invoke_arn_delete_todo = lookup(var.lambda_invoke_arns, "DeleteTodoFunction", "")
  })
}

resource "aws_api_gateway_rest_api" "this" {
  name        = "${var.environment}-todo-api"
  description = "Todo API backed by Lambda"

  body = local.openapi_spec

  endpoint_configuration {
    types = ["REGIONAL"]
  }
}

resource "aws_lambda_permission" "api_gateway" {
  for_each      = var.lambda_invoke_arns
  statement_id  = "AllowAPIGatewayInvoke${each.key}"
  action        = "lambda:InvokeFunction"
  function_name = "${var.environment}-${each.key}"
  principal     = "apigateway.amazonaws.com"

  source_arn = "${aws_api_gateway_rest_api.this.execution_arn}/*/*"
}

resource "aws_api_gateway_deployment" "this" {
  rest_api_id = aws_api_gateway_rest_api.this.id

  triggers = {
    redeployment = sha1(aws_api_gateway_rest_api.this.body)
  }

  lifecycle {
    create_before_destroy = true
  }
}

resource "aws_api_gateway_stage" "this" {
  rest_api_id   = aws_api_gateway_rest_api.this.id
  deployment_id = aws_api_gateway_deployment.this.id
  stage_name    = var.environment

  xray_tracing_enabled = true

  access_log_settings {
    destination_arn = aws_cloudwatch_log_group.apigw.arn

    format = jsonencode({
      requestId               = "$context.requestId"
      sourceIp                = "$context.identity.sourceIp"
      requestTime             = "$context.requestTime"
      protocol                = "$context.protocol"
      httpMethod              = "$context.httpMethod"
      resourcePath            = "$context.resourcePath"
      routeKey                = "$context.routeKey"
      status                  = "$context.status"
      responseLength          = "$context.responseLength"
      integrationErrorMessage = "$context.integrationErrorMessage"
    })
  }
}

resource "aws_cloudwatch_log_group" "apigw" {
  name              = "/aws/apigateway/${var.environment}-todo-api"
  retention_in_days = 7
}
