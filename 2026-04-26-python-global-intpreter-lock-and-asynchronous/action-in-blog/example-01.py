import asyncio


async def fetch_data():
  print("run fetch data")
  await asyncio.sleep(1)
  return "fetched data"


if __name__ == "__main__":
  coroutine = fetch_data()
  print(type(coroutine))
  result = asyncio.run(coroutine)
  print(result)