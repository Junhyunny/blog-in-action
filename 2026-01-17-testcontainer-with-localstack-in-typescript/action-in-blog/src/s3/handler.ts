import type {
  APIGatewayProxyResult,
  APIGatewayProxyWithCognitoAuthorizerEvent,
} from "aws-lambda";
import { getObject } from "../common/s3-client";
import {
  badRequest,
  internalServerError,
  notFound,
  ok,
} from "../common/responses";

export const handler = async (
  event: APIGatewayProxyWithCognitoAuthorizerEvent,
): Promise<APIGatewayProxyResult> => {
  try {
    const key = event.queryStringParameters?.key;
    if (!key) {
      return badRequest();
    }

    const response = await getObject(key);
    if (!response?.Body) {
      return notFound();
    }

    const byteArray = await response.Body.transformToByteArray();
    const buffer = Buffer.from(byteArray);
    const base64Body = buffer.toString("base64");

    return ok(base64Body, {
      header: {
        "Content-Type": response.ContentType || "application/octet-stream",
        "Content-Disposition": `attachment; filename="temp.txt"`,
        "Access-Control-Allow-Origin": "*",
      },
      isBase64Encoded: true,
    });
  } catch (e: unknown) {
    if (e instanceof Error && e["name"] === "NoSuchKey") {
      return notFound();
    }
    return internalServerError();
  }
};
