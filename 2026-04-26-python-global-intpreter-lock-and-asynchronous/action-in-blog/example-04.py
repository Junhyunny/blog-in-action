import asyncio


async def fetch_data():
  print("begin fetch data")
  await asyncio.sleep(1)
  print("end fetch data")
  return "fetched data"


async def main():
  coroutine = fetch_data()
  task = asyncio.create_task(coroutine)

  print("task created: ", task)
  print("task done: ", task.done())

  result = await task

  print("await result: ", result)
  print("task done: ", task.done())
  print("task.result(): ", task.result())

if __name__ == "__main__":
  main_coroutine = main()
  asyncio.run(main_coroutine)