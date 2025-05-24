import json


def lambda_handler(event, context):
	print(f"Hello, I am a second target. - {json.dumps(event)}")
	return {
		"statusCode": 200,
		"body"      : f"Hello, I am a second target. - {json.dumps(event)}"
	}
