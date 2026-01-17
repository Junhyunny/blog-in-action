import {
  BillingMode,
  CreateTableCommand,
  DeleteTableCommand,
  DynamoDBClient,
} from "@aws-sdk/client-dynamodb";
import {
  CreateBucketCommand,
  DeleteObjectCommand,
  ListObjectsV2Command,
  S3Client,
} from "@aws-sdk/client-s3";
import { LocalstackContainer } from "@testcontainers/localstack";
import type { StartedTestContainer } from "testcontainers";
import { afterAll, afterEach, beforeAll, beforeEach } from "vitest";
import {
  setClient as setDynamoDBClient,
  tableName,
} from "./common/dynamodb-client";
import { setClient as setS3Client } from "./common/s3-client";

const region: string = process.env.AWS_REGION || "ap-northeast-1";
export const testBucket: string = process.env.S3_BUCKET || "test-bucket";

let container: StartedTestContainer;
export let testDynamoDBClient: DynamoDBClient;
export let testS3Client: S3Client;

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
  testS3Client = new S3Client({ ...awsConfig, forcePathStyle: true });
  setS3Client(testS3Client);
  setDynamoDBClient(testDynamoDBClient);
  await createBucketIfNotExists();
}, 120000);

afterAll(async () => {
  if (container) {
    await container.stop();
  }
});

const createBucketIfNotExists = async () => {
  try {
    await testS3Client.send(
      new CreateBucketCommand({
        Bucket: testBucket,
      }),
    );
  } catch (e: unknown) {
    const error = e as Error;
    console.log(error.message);
  }
};

beforeEach(async () => {
  await emptyBucket();
  await createTable();
});

afterEach(async () => {
  await deleteTable();
});

const emptyBucket = async () => {
  const objects = await testS3Client.send(
    new ListObjectsV2Command({ Bucket: testBucket }),
  );
  if (!objects.Contents?.length) {
    return;
  }
  for (const content of objects.Contents) {
    await testS3Client.send(
      new DeleteObjectCommand({
        Bucket: testBucket,
        Key: content.Key,
      }),
    );
  }
};

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
