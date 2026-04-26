import asyncio


async def fetch_data():
  print("begin fetch data")
  await asyncio.sleep(1)
  print("end fetch data")
  return "fetched data"


async def main():
  coroutine = fetch_data()
  task = asyncio.create_task(coroutine)

  print("before await sleep 0 second")
  await asyncio.sleep(0)
  print("after await sleep 0 second")

  print("before await task")
  await task
  print("after await task")


if __name__ == "__main__":
  main_coroutine = main()
  asyncio.run(main_coroutine)