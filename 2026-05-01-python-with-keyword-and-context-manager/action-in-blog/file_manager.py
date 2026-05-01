class FileManager:
  def __init__(self):
    print("initialize file manager")
    pass

  def __enter__(self):
    print("open file manager")
    return self

  def __exit__(self, exc_type, exc_value, exc_traceback):
    if exc_type:
      print(f"handle exception: {exc_type}, {exc_value}, {exc_traceback}")
    print("close file manager")
    # return True # to suppress exception
