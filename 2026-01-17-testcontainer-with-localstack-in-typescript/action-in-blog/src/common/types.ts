export type BaseItem = {
  pk: string;
  sk: string;
};

// biome-ignore lint/suspicious/noExplicitAny: don't know what values will be stored
export type DynamoDbItem = BaseItem & Record<string, any>;
