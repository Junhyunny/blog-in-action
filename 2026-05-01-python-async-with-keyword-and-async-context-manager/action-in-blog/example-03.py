import sys
import asyncio
from async_session_manager import AssyncSessionManager


async def main():
  manager = AssyncSessionManager()
  try:
    m = await manager.__aenter__()
    print(f"do something with {m}")
    raise Exception("something went wrong")
  except:
    await manager.__aexit__(*sys.exc_info())
    raise
  else:
    await manager.__aexit__(None, None, None)


asyncio.run(main())
