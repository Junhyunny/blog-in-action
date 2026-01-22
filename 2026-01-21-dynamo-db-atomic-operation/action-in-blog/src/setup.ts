import {
  BillingMode,
  CreateTableCommand,
  DeleteTableCommand,
  DynamoDBClient,
} from "@aws-sdk/client-dynamodb";
import { LocalstackContainer } from "@testcontainers/localstack";
import type { StartedTestContainer } from "testcontainers";
import { afterAll, afterEach, beforeAll, beforeEach } from "vitest";

const region: string = process.env.AWS_REGION || "ap-northeast-1";
export const tableName: string = process.env.DYNAMODB_TABLE || "test-table";

let container: StartedTestContainer;
export let testDynamoDBClient: DynamoDBClient;

beforeAll(async () => {
  container = await new LocalstackContainer(
    "localstack/localstack:latest",
  ).start();
  const awsConfig = {
    endpoint: `http://${container.getHost()}:${container.getMappedPort(4566)}`,
    credentials: {
      accessKeyId: "test",
      secretAccessKey: "test",
    },
    region,
  };
  testDynamoDBClient = new DynamoDBClient(awsConfig);
}, 120000);

afterAll(async () => {
  if (container) {
    await container.stop();
  }
});

beforeEach(async () => {
  await createTable();
});

afterEach(async () => {
  await deleteTable();
});

const createTable = async () => {
  await testDynamoDBClient.send(
    new CreateTableCommand({
      TableName: tableName,
      BillingMode: BillingMode.PAY_PER_REQUEST,
      AttributeDefinitions: [
        { AttributeName: "pk", AttributeType: "S" },
        { AttributeName: "sk", AttributeType: "S" },
      ],
      KeySchema: [
        { AttributeName: "pk", KeyType: "HASH" },
        { AttributeName: "sk", KeyType: "RANGE" },
      ],
    }),
  );
};

const deleteTable = async () => {
  await testDynamoDBClient.send(
    new DeleteTableCommand({
      TableName: tableName,
    }),
  );
};
