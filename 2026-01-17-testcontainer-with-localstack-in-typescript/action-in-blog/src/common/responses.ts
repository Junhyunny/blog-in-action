import type { APIGatewayProxyResult } from "aws-lambda";

const defaultHeaders = {
  "Access-Control-Allow-Origin": "*",
};

export const ok = (
  body: string,
  options?: { header?: Record<string, string>; isBase64Encoded?: boolean },
): APIGatewayProxyResult => {
  return {
    statusCode: 200,
    headers: { ...defaultHeaders, ...options?.header },
    body,
    isBase64Encoded: options?.isBase64Encoded,
  };
};

export const badRequest = (): APIGatewayProxyResult => {
  return {
    statusCode: 400,
    headers: defaultHeaders,
    body: "Bad Request",
  };
};

export const notFound = (): APIGatewayProxyResult => {
  return {
    statusCode: 404,
    headers: defaultHeaders,
    body: "Not Found",
  };
};

export const internalServerError = (): APIGatewayProxyResult => {
  return {
    statusCode: 500,
    headers: defaultHeaders,
    body: "Internal Server Error",
  };
};
