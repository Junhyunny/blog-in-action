#!/bin/bash

BASE_URL="http://localhost:8000"

TMP_BODY=$(mktemp)
RESPONSE_BODY=""

request() {
  local method=$1
  local url=$2
  local data=$3

  if [ -n "$data" ]; then
    status=$(curl -s -o "$TMP_BODY" -w "%{http_code}" -X "$method" "$url" \
      -H "Content-Type: application/json" \
      -d "$data")
  else
    status=$(curl -s -o "$TMP_BODY" -w "%{http_code}" -X "$method" "$url")
  fi

  RESPONSE_BODY=$(cat "$TMP_BODY")

  echo "HTTP status: $status"
  if [ -n "$RESPONSE_BODY" ]; then
    echo "$RESPONSE_BODY" | python3 -m json.tool 2>/dev/null || echo "$RESPONSE_BODY"
  fi
}

echo "=== POST /todos (create first) ==="
request POST "$BASE_URL/todos" '{"title": "Buy groceries", "description": "Milk, eggs, bread"}'
TODO_ID_1=$RESPONSE_BODY

echo ""
echo "=== POST /todos (create second) ==="
request POST "$BASE_URL/todos" '{"title": "Read book", "description": "Clean Code"}'
TODO_ID_2=$RESPONSE_BODY

echo ""
echo "=== GET /todos (list all) ==="
request GET "$BASE_URL/todos"

echo ""
echo "=== GET /todos/$TODO_ID_1 (get by id) ==="
request GET "$BASE_URL/todos/$TODO_ID_1"

echo ""
echo "=== PATCH /todos/$TODO_ID_1 (update) ==="
request PATCH "$BASE_URL/todos/$TODO_ID_1" '{"title": "Buy groceries and cook", "completed": true}'

echo ""
echo "=== GET /todos/$TODO_ID_1 (verify update) ==="
request GET "$BASE_URL/todos/$TODO_ID_1"

echo ""
echo "=== DELETE /todos/$TODO_ID_2 ==="
request DELETE "$BASE_URL/todos/$TODO_ID_2"

echo ""
echo "=== GET /todos (verify delete) ==="
request GET "$BASE_URL/todos"

echo ""
echo "=== GET /todos/999 (not found) ==="
request GET "$BASE_URL/todos/999"
