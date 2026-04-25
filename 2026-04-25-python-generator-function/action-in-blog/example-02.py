import tracemalloc


def read_large_file(path):
  results = []
  with open(path) as f:
    for line in f:
      results.append(line.strip())
  return results


tracemalloc.start()
lines = read_large_file("big.txt")
print(f"첫 번째 줄: {lines[0]}")
current, peak = tracemalloc.get_traced_memory()
tracemalloc.stop()

print(f"현재 메모리 사용량: {current / 10**6:.2f} MB")
print(f"최대 메모리 사용량: {peak / 10**6:.2f} MB")
