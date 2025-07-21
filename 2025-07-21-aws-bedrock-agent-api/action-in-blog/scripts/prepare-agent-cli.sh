#!/bin/bash

AGENT_ID=$1

aws bedrock-agent prepare-agent \
  --region ap-northeast-1 \
  --agent-id "$AGENT_ID" | jq .