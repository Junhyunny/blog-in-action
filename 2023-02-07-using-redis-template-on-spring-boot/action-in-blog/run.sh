curl -X POST\
  -H "Content-Type: application/json"\
  -d '{"inviter": "Junhyunny", "invitee": "Jua"}'\
  http://localhost:8080/invitation

curl -X POST\
  -H "Content-Type: application/json"\
  -d '{"inviter": "Junhyunny", "invitee": "Jua"}'\
  http://localhost:8080/invitation/cancel

curl http://localhost:8080/user/messages/Jua | jq .