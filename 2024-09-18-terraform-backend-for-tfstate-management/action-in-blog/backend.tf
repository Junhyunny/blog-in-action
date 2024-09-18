resource "aws_s3_bucket" "state" {
  bucket = "${var.project_name}-tfstate-bucket"
}

resource "aws_s3_bucket_versioning" "versioning" {
  bucket = aws_s3_bucket.state.id

  versioning_configuration {
    status = "Enabled"
  }
}

resource "aws_dynamodb_table" "terraform_state_lock" {
  name         = "${var.project_name}-terraform-lock"
  hash_key     = "LockID"
  billing_mode = "PAY_PER_REQUEST"

  attribute {
    name = "LockID"
    type = "S"
  }
}

terraform {
  backend "s3" {
    bucket         = "junhyunny-demo-tfstate-bucket"
    key            = "terraform.tfstate"
    region         = "us-east-1"
    dynamodb_table = "junhyunny-demo-terraform-lock"
  }
}
