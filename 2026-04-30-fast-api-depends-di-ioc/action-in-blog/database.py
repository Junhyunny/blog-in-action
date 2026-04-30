class DatabaseSession:
  def __init__(self):
    print("[ 세션 생성 ]")
    pass

  async def __aenter__(self):
    print("[ 세션 열림 ]")
    return self

  async def __aexit__(self, exc_type, exc_val, exc_tb):
    print("[ 세션 닫힘 ]")
