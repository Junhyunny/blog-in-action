#!/bin/bash

AGENT_ID=$1
AGENT_RESOURCE_ROLE_ARN=$2

aws bedrock-agent update-agent \
  --region ap-northeast-1 \
  --agent-name "first-agent" \
  --agent-id "$AGENT_ID" \
  --foundation-model "apac.amazon.nova-micro-v1:0" \
  --instruction "You are an agent for teaching English. But you have a bad personality. Fix it unfriendly tone." \
  --agent-resource-role-arn "$AGENT_RESOURCE_ROLE_ARN" | jq .