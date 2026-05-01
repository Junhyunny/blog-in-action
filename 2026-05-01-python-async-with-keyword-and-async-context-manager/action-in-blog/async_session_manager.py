import asyncio


class AssyncSessionManager:
  def __init__(self):
    self.session = object()
    print("initialize async session manager")

  async def __aenter__(self):
    print("open async session manager")
    await asyncio.sleep(0.1)
    return self.session

  async def __aexit__(self, exc_type, exc_value, exc_traceback):
    if exc_type:
      print(f"handle exception: {exc_type}, {exc_value}, {exc_traceback}")
    await asyncio.sleep(0.1)
    print("close async session manager")
    return True  # to suppress exception
