#!/bin/bash

AGENT_ID=$1
NAME=$2

aws bedrock-agent create-agent-alias \
  --region ap-northeast-1 \
  --agent-id "$AGENT_ID" \
  --agent-alias-name "$NAME" | jq .
