def my_generator():
  x = 10
  y = 20
  yield x + y  # 첫 번째 yield
  z = 30
  yield x + y + z  # 두 번째 yield


# ─────────────────────────────────
gen = my_generator()
print(next(gen))  # 첫 번째 호출 → 30
print(next(gen))  # 두 번째 호출 → 60
print(next(gen))  # StopIteration
