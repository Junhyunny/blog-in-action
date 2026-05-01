import asyncio
from async_session_manager_contextlib import async_session


async def main():
  async with async_session() as session:
    print(f"do something with {session}")
    raise Exception("something went wrong")


asyncio.run(main())
