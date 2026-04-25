def my_generator():
  x = 10
  y = 20
  yield x + y
  z = 30
  yield x + y + z


# ─────────────────────────────────
gen = my_generator()

print("=" * 50)
print("▶ 제네레이터 객체 생성 직후 (아직 실행 안됨)")
print("=" * 50)

frame = gen.gi_frame
print(f"프레임 존재 여부 : {frame}")
print(f"현재 실행 줄 번호 : {frame.f_lineno}")
print(f"로컬 변수 : {frame.f_locals}")
print(f"제네레이터 실행 중 : {gen.gi_running}")

print()
print("=" * 50)
print("▶ 첫 번째 next() 호출")
print("=" * 50)
print(f"yield 값 : {next(gen)}")
frame = gen.gi_frame
print(f"현재 실행 줄 번호 : {frame.f_lineno}")
print(f"로컬 변수 : {frame.f_locals}")

print()
print("=" * 50)
print("▶ 두 번째 next() 호출")
print("=" * 50)
print(f"yield 값 : {next(gen)}")
frame = gen.gi_frame
print(f"현재 실행 줄 번호 : {frame.f_lineno}")
print(f"로컬 변수 : {frame.f_locals}")

print()
print("=" * 50)
print("▶ 세 번째 next() 호출, 제네레이터 종료")
print("=" * 50)
try:
  next(gen)
except StopIteration:
  print("StopIteration 발생")
  print(f"프레임 존재 여부 : {gen.gi_frame}")
