async def fetch_data():
  print("run fetch_data")
  return "returned fetched data"


if __name__ == "__main__":
  coroutine = fetch_data()
  try:
    coroutine.send(None)
  except StopIteration as e:
    print(f"StopIteration: {e.value}")