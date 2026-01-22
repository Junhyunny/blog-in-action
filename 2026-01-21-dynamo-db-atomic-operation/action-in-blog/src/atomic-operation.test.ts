import { it } from "vitest";
import { tableName, testDynamoDBClient } from "./setup";
import { GetCommand, PutCommand, UpdateCommand } from "@aws-sdk/lib-dynamodb";

const unsafeUpdate = async (newItem: string) => {
  const { Item } = await testDynamoDBClient.send(
    new GetCommand({
      TableName: tableName,
      Key: { pk: "pk", sk: "sk" },
    }),
  );

  if (!Item) {
    return;
  }

  const newList = [...Item.myList, newItem];

  const command = new UpdateCommand({
    TableName: tableName,
    Key: { pk: "pk", sk: "sk" },
    UpdateExpression: "SET myList = :newList",
    ExpressionAttributeValues: {
      ":newList": newList,
    },
  });
  await testDynamoDBClient.send(command);
};

const atomicUpdate = async (newItem: string) => {
  const command = new UpdateCommand({
    TableName: tableName,
    Key: { pk: "pk", sk: "sk" },
    UpdateExpression: "ADD mySet :newItem",
    ExpressionAttributeValues: {
      ":newItem": new Set([newItem]),
    },
  });
  await testDynamoDBClient.send(command);
};

it("atomic operation test", async () => {
  await testDynamoDBClient.send(
    new PutCommand({
      TableName: tableName,
      Item: { pk: "pk", sk: "sk", myList: [] },
    }),
  );
  const unsafeUpdatePromise = [];
  const atomicUpdatePromise = [];

  for (let index = 0; index < 100; index++) {
    unsafeUpdatePromise.push(unsafeUpdate(`item-${index}`));
    atomicUpdatePromise.push(atomicUpdate(`item-${index}`));
  }

  await Promise.all([...unsafeUpdatePromise, ...atomicUpdatePromise]);

  const { Item } = await testDynamoDBClient.send(
    new GetCommand({
      TableName: tableName,
      Key: { pk: "pk", sk: "sk" },
    }),
  );
  console.log("unsafe update result:", Item?.myList.length);
  console.log("atomic update result:", Item?.mySet.size);
});
