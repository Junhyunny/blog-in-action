#!/bin/bash

AGENT_ID=$1
AGENT_ALIAS_NAME=$2

aws bedrock-agent prepare-agent \
  --region ap-northeast-1 \
  --agent-id "$AGENT_ID" | jq .

sleep 3

aws bedrock-agent create-agent-alias \
  --region ap-northeast-1 \
  --agent-id "$AGENT_ID" \
  --agent-alias-name "$AGENT_ALIAS_NAME" | jq .