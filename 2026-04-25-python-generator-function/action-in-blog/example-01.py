def number_generator():
  n = 0
  print("start generating number")
  while True:
    print("generating number: ", n)
    yield n
    n += 1


if __name__ == "__main__":
  gen = number_generator()
  print(type(gen))
  print(next(gen))  # 0
  print(next(gen))  # 1
  print(next(gen))  # 2
  # ...
  for i in gen:
    print(i)
    if i > 4:
      break
