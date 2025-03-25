variable "region" {
  default = "ap-northeast-1"
  type    = string
}

variable "vpc_name" {
  default = "project-vpc"
  type    = string
}

variable "project_name" {
  default = "project"
  type    = string
}

variable "google_client_id" {
  default = "google_client_id"
  type    = string
}

variable "google_client_secret" {
  default = "google_client_secret"
  type    = string
}

variable "acm_arn" {
  default = "acm_arn"
  type    = string
}