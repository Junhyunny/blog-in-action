import type {
  APIGatewayProxyResult,
  APIGatewayProxyWithCognitoAuthorizerEvent,
} from "aws-lambda";
import { putItem } from "../common/dynamodb-client";
import { badRequest, internalServerError, ok } from "../common/responses";

export const handler = async (
  event: APIGatewayProxyWithCognitoAuthorizerEvent,
): Promise<APIGatewayProxyResult> => {
  try {
    const body = JSON.parse(event.body || "{}");
    if (!body.name) {
      return badRequest();
    }
    await putItem({ pk: "USER", sk: `NAME#${body.name}`, name: body.name });
    return ok(JSON.stringify({ message: "ok" }));
  } catch (_e: unknown) {
    return internalServerError();
  }
};
