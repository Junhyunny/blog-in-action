aws dynamodb create-table\
  --table-name TodoTable_20240925\
  --attribute-definitions '[{"AttributeName":"PK","AttributeType":"S"}, {"AttributeName":"SK","AttributeType":"S"}]'\
  --key-schema AttributeName=PK,KeyType=HASH AttributeName=SK,KeyType=RANGE\
  --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1\
  --endpoint-url http://dynamodb-local:8000
