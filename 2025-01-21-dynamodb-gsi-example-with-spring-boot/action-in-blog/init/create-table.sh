aws dynamodb create-table \
    --table-name ActorsPortfolioTable \
    --attribute-definitions AttributeName=Actor,AttributeType=S \
        AttributeName=Movie,AttributeType=S \
    --key-schema AttributeName=Actor,KeyType=HASH AttributeName=Movie,KeyType=RANGE \
    --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1 \
    --endpoint-url http://dynamodb-local:8000
