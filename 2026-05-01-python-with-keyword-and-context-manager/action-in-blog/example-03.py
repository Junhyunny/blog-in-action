import sys
from file_manager import FileManager

manager = FileManager()
try:
  m = manager.__enter__()
  print(f"do something with {m}")
  raise Exception("something went wrong")
except:
  manager.__exit__(*sys.exc_info())
  raise
else:
  manager.__exit__(None, None, None)
