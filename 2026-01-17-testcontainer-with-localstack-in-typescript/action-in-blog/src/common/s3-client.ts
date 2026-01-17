import {
  GetObjectCommand,
  type GetObjectCommandInput,
  type GetObjectCommandOutput,
  S3Client,
} from "@aws-sdk/client-s3";

let s3Client: S3Client;

const endpoint = process.env.S3_ENDPOINT || "http://localhost:4566";
const region = process.env.AWS_REGION || "ap-northeast-1";
export const bucketName = process.env.S3_BUCKET || "test-bucket";

const client = () => {
  if (!s3Client) {
    s3Client = new S3Client({ region, endpoint, forcePathStyle: true });
  }
  return s3Client;
};

export const setClient = (client: S3Client) => {
  s3Client = client;
};

export const getObject = async (
  key: string,
): Promise<GetObjectCommandOutput> => {
  const s3 = client();
  const commandInput: GetObjectCommandInput = {
    Bucket: bucketName,
    Key: key,
  };
  const command = new GetObjectCommand(commandInput);
  return await s3.send(command);
};
