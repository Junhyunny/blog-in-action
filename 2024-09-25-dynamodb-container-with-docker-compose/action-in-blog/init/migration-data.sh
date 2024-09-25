aws dynamodb put-item\
  --table-name TodoTable_20240925\
  --item file://./init/data/todo.json\
  --endpoint-url http://dynamodb-local:8000
