import { ScanCommand } from "@aws-sdk/lib-dynamodb";
import type { APIGatewayProxyResult } from "aws-lambda";
import { docClient, TABLE_NAME } from "../common/dynamodb-client";

export const lambdaHandler = async (): Promise<APIGatewayProxyResult> => {
  try {
    const command = new ScanCommand({
      TableName: TABLE_NAME,
    });

    const result = await docClient.send(command);
    const todos = result.Items || [];

    return {
      statusCode: 200,
      headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": "*",
      },
      body: JSON.stringify(
        todos.sort(
          (a, b) => a.createdAt.toString().localeCompare(b.createdAt) * -1,
        ),
      ),
    };
  } catch (err) {
    console.error(err);
    return {
      statusCode: 500,
      headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": "*",
      },
      body: JSON.stringify({
        message: "Internal server error",
      }),
    };
  }
};
