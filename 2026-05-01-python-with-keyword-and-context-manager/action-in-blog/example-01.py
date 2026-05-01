from file_manager import FileManager

manager = FileManager()
try:
  m = manager.__enter__()
  print(f"do something with {m}")
finally:
  manager.__exit__(None, None, None)
