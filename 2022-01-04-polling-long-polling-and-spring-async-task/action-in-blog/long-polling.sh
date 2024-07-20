URL="http://localhost:8080/messages/1"
END_CONDITION="END"

while true; do
  RESPONSE=$(curl -m 10 -s $URL)
  CURL_EXIT_STATUS=$?
  if [ $CURL_EXIT_STATUS -eq 28 ]; then
    echo "Request timed out."
  elif [[ "$RESPONSE" == *"$END_CONDITION"* ]]; then
    echo "Condition met, exiting the loop."
    break
  else
    echo "Response: $RESPONSE"
  fi
done

echo "Polling complete."
