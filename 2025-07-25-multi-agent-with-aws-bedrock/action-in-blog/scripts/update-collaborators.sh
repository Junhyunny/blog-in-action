#!/bin/bash

SUPERVISOR_ID=$1
COLLABORATOR_NAME=$2
COLLABORATOR_AGENT_ALIAS_ARN=$3

if [[ "$COLLABORATOR_NAME" != research-agent* && "$COLLABORATOR_NAME" != chart-agent* ]]; then
  echo "❌ Usage: <AGENT_NAME> must start with 'research-agent' or 'chart-agent'"
  exit 1
fi

if [ "$COLLABORATOR_NAME" = "research-agent" ]; then
  INSTRUCTION=$(cat ./prompts/collaborator-instruction-research.txt)
else
  INSTRUCTION=$(cat ./prompts/collaborator-instruction-chart.txt)
fi

aws bedrock-agent associate-agent-collaborator \
  --region ap-northeast-1 \
  --agent-id "$SUPERVISOR_ID" \
  --agent-version DRAFT \
  --collaborator-name "$COLLABORATOR_NAME" \
  --agent-descriptor "aliasArn=$COLLABORATOR_AGENT_ALIAS_ARN" \
  --collaboration-instruction "$INSTRUCTION" \
  --relay-conversation-history TO_COLLABORATOR | jq .
