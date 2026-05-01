from contextlib import contextmanager


@contextmanager
def file_manager():
  print("initialize file manager")  # __init__ 역할
  print("open file manager")  # __enter__ 역할
  try:
    yield  # as 변수에 매칭되는 반환 (__enter__의 return 에 해당)
  except Exception as e:  # __exit__ 예외 처리 역할
    print(f"handle exception: {type(e)}, {e}")
    # pass # to suppress exception
    raise  # to propagate exception
  finally:
    print("close file manager")  # __exit__ 역할
