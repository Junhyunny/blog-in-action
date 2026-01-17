import { GetCommand, ScanCommand } from "@aws-sdk/lib-dynamodb";
import type { APIGatewayProxyWithCognitoAuthorizerEvent } from "aws-lambda";
import { expect, test } from "vitest";
import { tableName } from "../common/dynamodb-client";
import { testDynamoDBClient } from "../setup";
import { handler } from "./handler";

test("save user information then respond ok", async () => {
  const event = {
    body: '{"name":"jun"}',
  } as unknown as APIGatewayProxyWithCognitoAuthorizerEvent;

  const response = await handler(event);

  expect(response).toEqual({
    statusCode: 200,
    headers: { "Access-Control-Allow-Origin": "*" },
    body: '{"message":"ok"}',
  });
  const result = await testDynamoDBClient.send(
    new GetCommand({
      TableName: tableName,
      Key: { pk: "USER", sk: "NAME#jun" },
    }),
  );
  expect(result.Item).toEqual({
    pk: "USER",
    sk: "NAME#jun",
    name: "jun",
  });
});

test("body is empty then respond bad request", async () => {
  const event = {} as unknown as APIGatewayProxyWithCognitoAuthorizerEvent;

  const response = await handler(event);

  expect(response).toEqual({
    statusCode: 400,
    headers: { "Access-Control-Allow-Origin": "*" },
    body: "Bad Request",
  });
  const result = await testDynamoDBClient.send(
    new ScanCommand({
      TableName: tableName,
    }),
  );
  expect(result.Items?.length).toEqual(0);
});
