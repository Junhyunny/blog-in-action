import { DynamoDBClient } from "@aws-sdk/client-dynamodb";
import { DynamoDBDocumentClient, PutCommand } from "@aws-sdk/lib-dynamodb";
import type { DynamoDbItem } from "./types";

let dynamodbClient: DynamoDBClient;

const endpoint = process.env.DYNAMODB_ENDPOINT || "http://localstack:4566";
const region = process.env.AWS_REGION || "ap-northeast-1";
export const tableName = process.env.DYNAMODB_TABLE || "test";

const client = (): DynamoDBDocumentClient => {
  if (!dynamodbClient) {
    dynamodbClient = new DynamoDBClient({ region, endpoint });
  }
  return DynamoDBDocumentClient.from(dynamodbClient);
};

export const setClient = (client: DynamoDBClient) => {
  dynamodbClient = client;
};

export const putItem = async (item: DynamoDbItem) => {
  const ddbClient = client();
  const putItemCommand = new PutCommand({ TableName: tableName, Item: item });
  await ddbClient.send(putItemCommand);
};
