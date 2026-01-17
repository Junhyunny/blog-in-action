import {
  PutObjectCommand,
  type PutObjectCommandInput,
} from "@aws-sdk/client-s3";
import type { APIGatewayProxyWithCognitoAuthorizerEvent } from "aws-lambda";
import { expect, test } from "vitest";
import { bucketName } from "../common/s3-client";
import { testS3Client } from "../setup";
import { handler } from "./handler";

test("get download file then respond ok with file data", async () => {
  const input: PutObjectCommandInput = {
    Bucket: bucketName,
    Key: "foo/bar/temp.txt",
    ContentType: "text/plain",
    Body: Buffer.from("hello"),
  };
  await testS3Client.send(new PutObjectCommand(input));
  const event = {
    queryStringParameters: {
      key: "foo/bar/temp.txt",
    },
  } as unknown as APIGatewayProxyWithCognitoAuthorizerEvent;

  const result = await handler(event);

  expect(result).toEqual({
    statusCode: 200,
    headers: {
      "Content-Type": "text/plain",
      "Content-Disposition": 'attachment; filename="temp.txt"',
      "Access-Control-Allow-Origin": "*",
    },
    body: "aGVsbG8=",
    isBase64Encoded: true,
  });
});

test("key is undefined then respond bad request", async () => {
  const event = {} as unknown as APIGatewayProxyWithCognitoAuthorizerEvent;

  const response = await handler(event);

  expect(response).toEqual({
    statusCode: 400,
    headers: {
      "Access-Control-Allow-Origin": "*",
    },
    body: "Bad Request",
  });
});

test("item is not existed in s3 then respond not found", async () => {
  const event = {
    queryStringParameters: {
      key: "foo/bar/temp.txt",
    },
  } as unknown as APIGatewayProxyWithCognitoAuthorizerEvent;

  const response = await handler(event);

  expect(response).toEqual({
    statusCode: 404,
    headers: {
      "Access-Control-Allow-Origin": "*",
    },
    body: "Not Found",
  });
});
