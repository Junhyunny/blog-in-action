import asyncio
from async_session_manager import AssyncSessionManager


async def main():
  async with AssyncSessionManager() as m:
    print(f"do something with {m}")


asyncio.run(main())
