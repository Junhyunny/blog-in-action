#!/bin/bash

AGENT_ID=$1
REGION="ap-northeast-1"

if [ -z "$AGENT_ID" ] || [ -z "$REGION" ]; then
  echo "❗ Usage: $0 <AGENT_ID> <REGION>"
  exit 1
fi

echo "📋 [1/3] Listing aliases for Agent ID: $AGENT_ID"

ALIASES=$(aws bedrock-agent list-agent-aliases \
  --agent-id "$AGENT_ID" \
  --region "$REGION" \
  --query 'agentAliasSummaries[*].agentAliasId' \
  --output text 2>/dev/null)

if [ $? -ne 0 ]; then
  echo "⚠️  Failed to retrieve alias list. Continuing..."
  ALIASES=""
fi

if [ -z "$ALIASES" ]; then
  echo "✅ No aliases found. Proceeding to delete agent."
else
  echo "🗑 [2/3] Deleting aliases..."
  for ALIAS_ID in $ALIASES; do
    echo "   🔹 Deleting alias: $ALIAS_ID"
    aws bedrock-agent delete-agent-alias \
      --agent-id "$AGENT_ID" \
      --agent-alias-id "$ALIAS_ID" \
      --region "$REGION" >/dev/null 2>&1
    if [ $? -ne 0 ]; then
      echo "   ⚠️  Failed to delete alias $ALIAS_ID (continuing)"
    else
      echo "   ✅ Alias $ALIAS_ID deleted"
    fi
  done
fi

echo "🧨 [3/3] Deleting agent: $AGENT_ID"
aws bedrock-agent delete-agent \
  --agent-id "$AGENT_ID" \
  --region "$REGION" >/dev/null 2>&1

if [ $? -ne 0 ]; then
  echo "❌ Failed to delete agent. It may still have dependencies."
else
  echo "✅ Agent $AGENT_ID deleted successfully."
fi