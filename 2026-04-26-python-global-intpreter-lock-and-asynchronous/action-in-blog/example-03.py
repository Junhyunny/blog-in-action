import asyncio


async def fetch_data():
  print("begin fetch data")
  await asyncio.sleep(1)
  print("end fetch data")
  return "fetched data"


if __name__ == "__main__":
  coroutine = fetch_data()
  try:
    coroutine.send(None)
  except StopIteration as e:
    print(f"StopIteration: {e.value}")
  except Exception as e:
    print(f"Exception: {e}")