#!/bin/bash

AGENT_RESOURCE_ROLE_ARN=$1
AGENT_NAME=$2
AGENT_ALIAS_NAME=$3

if [ $# -lt 3 ]; then
  echo "❌ Usage: $0 <AGENT_RESOURCE_ROLE_ARN> <AGENT_NAME> <AGENT_ALIAS_NAME>"
  exit 1
fi

if [[ "$AGENT_NAME" != "research-agent" && "$AGENT_NAME" != "chart-agent" ]]; then
  echo "❌ Usage: <AGENT_NAME> must be 'research-agent' or 'chart-agent'"
  exit 1
fi

if [ "$AGENT_NAME" = "research-agent" ]; then
  INSTRUCTION=$(cat ./prompts/instruction-research.txt)
else
  INSTRUCTION=$(cat ./prompts/instruction-chart.txt)
fi

AGENT_ID=$(aws bedrock-agent create-agent \
  --region ap-northeast-1 \
  --agent-name "$AGENT_NAME" \
  --foundation-model "anthropic.claude-3-haiku-20240307-v1:0" \
  --instruction "$INSTRUCTION" \
  --agent-resource-role-arn "$AGENT_RESOURCE_ROLE_ARN" | jq -r '.agent.agentId')

aws bedrock-agent prepare-agent \
  --region ap-northeast-1 \
  --agent-id "$AGENT_ID" > /dev/null 2>&1

sleep 3

AGENT_ALIAS_ARN=$(aws bedrock-agent create-agent-alias \
  --region ap-northeast-1 \
  --agent-id "$AGENT_ID" \
  --agent-alias-name "$AGENT_ALIAS_NAME" | jq -r '.agentAlias.agentAliasArn')

echo "CREATED AGENT_ID = $AGENT_ID"
echo "CREATED AGENT_ALIAS_ARN = $AGENT_ALIAS_ARN"
