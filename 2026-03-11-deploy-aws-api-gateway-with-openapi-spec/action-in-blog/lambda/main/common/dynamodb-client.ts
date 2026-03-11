import { DynamoDBClient } from "@aws-sdk/client-dynamodb";
import { DynamoDBDocumentClient } from "@aws-sdk/lib-dynamodb";

const isLocal =
  process.env.AWS_SAM_LOCAL === "true" || process.env.IS_LOCAL === "true";

const dynamoDBClient = new DynamoDBClient(
  isLocal
    ? {
        endpoint:
          process.env.DYNAMODB_ENDPOINT || "http://host.docker.internal:4566",
        region: "ap-northeast-2",
        credentials: {
          accessKeyId: "test",
          secretAccessKey: "test",
        },
      }
    : {
        region: process.env.AWS_REGION || "ap-northeast-2",
      },
);

export const docClient = DynamoDBDocumentClient.from(dynamoDBClient, {
  marshallOptions: {
    removeUndefinedValues: true,
    convertEmptyValues: false,
  },
  unmarshallOptions: {
    wrapNumbers: false,
  },
});

export const TABLE_NAME = process.env.TABLE_NAME || "TodosTable";
