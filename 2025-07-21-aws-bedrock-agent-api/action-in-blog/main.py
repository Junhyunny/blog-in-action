import os
import sys
import uuid

import boto3
from dotenv import load_dotenv

load_dotenv()


def main(sentence):
	client = boto3.client("bedrock-agent-runtime", region_name = "ap-northeast-1")

	agent_id = os.getenv("AGENT_ID")
	agent_alias_id = os.getenv("AGENT_ALIAS_ID")

	response = client.invoke_agent(
		agentId = agent_id,
		agentAliasId = agent_alias_id,
		sessionId = f"{uuid.uuid4()}",
		inputText = f"Please check this sentence and explain why. Suggest nice expression with correct grammar. '{sentence}'"
	)

	for event in response['completion']:
		if "chunk" in event:
			print(event["chunk"]["bytes"].decode("utf-8"), end = "")


if __name__ == '__main__':
	if len(sys.argv) < 2:
		raise Exception("Please specify a sentence")
	main(sys.argv[1])
