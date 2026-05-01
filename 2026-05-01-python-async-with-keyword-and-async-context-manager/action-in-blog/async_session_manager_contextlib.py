import asyncio
from contextlib import asynccontextmanager


@asynccontextmanager
async def async_session():
  session = object()
  print("initialize async session")  # __init__ 역할
  print("open async session")  # __aenter__ 역할
  await asyncio.sleep(0.1)
  try:
    yield session
  except Exception as e:  # __aexit__ 예외 처리 역할
    print(f"handle exception: {type(e)}, {e}")
    # raise를 제거하고 pass를 사용하면 예외를 무시한다.
    raise  # to propagate exception
  finally:
    print("close async session")  # __aexit__ 역할
