from file_manager_contextlib import file_manager


with file_manager() as m:
  print(f"do something with {m}")
  raise Exception("something went wrong")
