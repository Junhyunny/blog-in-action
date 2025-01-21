aws dynamodb update-table \
    --table-name ActorsPortfolioTable \
    --attribute-definitions \
        AttributeName=Genre,AttributeType=S \
        AttributeName=Year,AttributeType=N \
    --global-secondary-index-updates \
        "[{
              \"Create\": {
                  \"IndexName\": \"GenreYearIndex\",
                \"KeySchema\": [
                      {\"AttributeName\": \"Genre\", \"KeyType\": \"HASH\"},
                    {\"AttributeName\": \"Year\", \"KeyType\": \"RANGE\"}
                ],
                \"Projection\": {
                      \"ProjectionType\": \"ALL\"
                },
                \"ProvisionedThroughput\": {
                      \"ReadCapacityUnits\": 1,
                    \"WriteCapacityUnits\": 1
                }
            }
        }]" \
    --endpoint-url http://dynamodb-local:8000
