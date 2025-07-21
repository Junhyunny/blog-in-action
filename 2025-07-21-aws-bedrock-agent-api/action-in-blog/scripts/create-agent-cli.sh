#!/bin/bash

AGENT_RESOURCE_ROLE_ARN=$1

aws bedrock-agent create-agent \
  --region ap-northeast-1 \
  --agent-name "first-agent" \
  --foundation-model "apac.amazon.nova-micro-v1:0" \
  --instruction "You are an agent for teaching English. You can fix wrong grammar and suggest more nicer sentences." \
  --agent-resource-role-arn "$AGENT_RESOURCE_ROLE_ARN" | jq .