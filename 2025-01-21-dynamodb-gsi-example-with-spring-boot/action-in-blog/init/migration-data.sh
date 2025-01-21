aws dynamodb put-item \
      --table-name ActorsPortfolioTable \
      --item '{"Actor": {"S": "Tom Hanks"}, "Movie": {"S": "Forrest Gump"}, "Year": {"N": "1994"}, "Role": {"S": "Forrest"}, "Genre": {"S": "Drama"}}' \
      --endpoint-url http://dynamodb-local:8000

aws dynamodb put-item \
      --table-name ActorsPortfolioTable \
      --item '{"Actor": {"S": "Tom Hanks"}, "Movie": {"S": "Cast Away"}, "Year": {"N": "2000"}, "Role": {"S": "Chunck Noland"}, "Genre": {"S": "Drama"}}' \
      --endpoint-url http://dynamodb-local:8000

aws dynamodb put-item \
      --table-name ActorsPortfolioTable \
      --item '{"Actor": {"S": "Tom Hanks"}, "Movie": {"S": "Toy Story"}, "Year": {"N": "1995"}, "Role": {"S": "Woody"}, "Genre": {"S": "Children"}}' \
      --endpoint-url http://dynamodb-local:8000

aws dynamodb put-item \
      --table-name ActorsPortfolioTable \
      --item '{"Actor": {"S": "Tim Allen"}, "Movie": {"S": "Toy Story"}, "Year": {"N": "1995"}, "Role": {"S": "Buzz Lightyear"}, "Genre": {"S": "Children"}}' \
      --endpoint-url http://dynamodb-local:8000

aws dynamodb put-item \
      --table-name ActorsPortfolioTable \
      --item '{"Actor": {"S": "Natalie Portman"}, "Movie": {"S": "Black Swan"}, "Year": {"N": "2010"}, "Role": {"S": "Nina Sayers"}, "Genre": {"S": "Drama"}}' \
      --endpoint-url http://dynamodb-local:8000
