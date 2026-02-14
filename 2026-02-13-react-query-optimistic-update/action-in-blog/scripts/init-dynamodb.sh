#!/bin/bash

echo "Initializing DynamoDB tables..."

# DynamoDB 테이블 생성
awslocal dynamodb create-table \
    --table-name TodosTable \
    --attribute-definitions \
        AttributeName=id,AttributeType=N \
    --key-schema \
        AttributeName=id,KeyType=HASH \
    --billing-mode PAY_PER_REQUEST \
    --region ap-northeast-2

echo "DynamoDB table 'TodosTable' created successfully!"

# 샘플 데이터 추가 (선택사항)
awslocal dynamodb put-item \
    --table-name TodosTable \
    --item '{
        "id": {"N": "1"},
        "title": {"S": "LocalStack으로 DynamoDB 연동하기"},
        "completed": {"BOOL": false},
        "createdAt": {"S": "'$(date -u +"%Y-%m-%dT%H:%M:%S.000Z")'"}
    }' \
    --region ap-northeast-2

awslocal dynamodb put-item \
    --table-name TodosTable \
    --item '{
        "id": {"N": "2"},
        "title": {"S": "Lambda 함수 작성하기"},
        "completed": {"BOOL": false},
        "createdAt": {"S": "'$(date -u +"%Y-%m-%dT%H:%M:%S.000Z")'"}
    }' \
    --region ap-northeast-2

echo "Sample data added successfully!"

