import { expect, it } from "vitest";
import { dynamoDBClient, tableName } from "./setup";
import { PutCommand, QueryCommand } from "@aws-sdk/lib-dynamodb";

type Item = { pk: string; sk: string; item: string };

const getAllItems = async (): Promise<Item[]> => {
  // biome-ignore lint/suspicious/noExplicitAny: unknown key
  let exclusiveStartKey: Record<string, any> | undefined;
  const result: Item[] = [];
  do {
    const command = new QueryCommand({
      TableName: tableName,
      KeyConditionExpression: "pk = :pk and begins_with(sk, :sk)",
      ExpressionAttributeValues: {
        ":pk": "pk",
        ":sk": "sk",
      },
      ExclusiveStartKey: exclusiveStartKey,
    });
    const output = await dynamoDBClient.send(command);
    result.push(...(output.Items as Item[]));
    exclusiveStartKey = output.LastEvaluatedKey;
  } while (exclusiveStartKey);
  return result;
};

it("given data size over 1MB when getAllItems then return get all items included next page", async () => {
  // 1 - 395KB size item
  const item = Array.from({ length: 395 * 1024 }, (_, __) => "0").join("");
  for (let index = 0; index < 4; index++) {
    await dynamoDBClient.send(
      new PutCommand({
        TableName: tableName,
        Item: {
          pk: "pk",
          sk: `sk-${index}`,
          item,
        },
      }),
    );
  }

  // 2
  const result = await getAllItems();

  // 3
  expect(result.length).toEqual(4);
  expect(result).toEqual([
    { pk: "pk", sk: "sk-0", item },
    { pk: "pk", sk: "sk-1", item },
    { pk: "pk", sk: "sk-2", item },
    { pk: "pk", sk: "sk-3", item },
  ]);
});

// biome-ignore lint/suspicious/noExplicitAny: unknown key
type PageItems = { items: Item[]; next: Record<string, any> | undefined };

const getItems = async (
  size: number,
  // biome-ignore lint/suspicious/noExplicitAny: unknown key
  next?: Record<string, any>,
): Promise<PageItems> => {
  const command = new QueryCommand({
    TableName: tableName,
    KeyConditionExpression: "pk = :pk and begins_with(sk, :sk)",
    ExpressionAttributeValues: {
      ":pk": "pk",
      ":sk": "sk",
    },
    ExclusiveStartKey: next,
    Limit: size,
  });
  const output = await dynamoDBClient.send(command);
  return { items: output.Items as Item[], next: output.LastEvaluatedKey };
};

it("getItems with page size", async () => {
  // 1
  for (let index = 0; index < 5; index++) {
    await dynamoDBClient.send(
      new PutCommand({
        TableName: tableName,
        Item: {
          pk: "pk",
          sk: `sk-${index}`,
          item: `item-${index}`,
        },
      }),
    );
  }

  // 2
  const firstResult = await getItems(2);
  const secondResult = await getItems(3, firstResult.next);
  const thirdResult = await getItems(1, secondResult.next);

  // 3
  expect(firstResult.next).toEqual({ pk: "pk", sk: "sk-1" });
  expect(firstResult.items.length).toEqual(2);
  expect(firstResult.items).toEqual([
    { pk: "pk", sk: "sk-0", item: "item-0" },
    { pk: "pk", sk: "sk-1", item: "item-1" },
  ]);
  expect(secondResult.next).toEqual({ pk: "pk", sk: "sk-4" });
  expect(secondResult.items.length).toEqual(3);
  expect(secondResult.items).toEqual([
    { pk: "pk", sk: "sk-2", item: "item-2" },
    { pk: "pk", sk: "sk-3", item: "item-3" },
    { pk: "pk", sk: "sk-4", item: "item-4" },
  ]);
  expect(thirdResult.next).toBeUndefined();
  expect(thirdResult.items.length).toEqual(0);
  expect(thirdResult.items).toEqual([]);
});
