module "dynamodb" {
  source      = "../modules/dynamodb"
  environment = var.environment
}

module "iam" {
  source             = "../modules/iam"
  environment        = var.environment
  dynamodb_table_arn = module.dynamodb.table_arn
  depends_on         = [module.dynamodb]
}

module "lambda" {
  source           = "../modules/lambda"
  environment      = var.environment
  region           = var.region
  lambda_functions = var.lambda_functions
  lambda_roles     = module.iam.lambda_role
  depends_on       = [module.iam]
}

module "api_gw" {
  source             = "../modules/apigw"
  environment        = var.environment
  lambda_invoke_arns = module.lambda.invoke_arns
  depends_on         = [module.lambda]
}
