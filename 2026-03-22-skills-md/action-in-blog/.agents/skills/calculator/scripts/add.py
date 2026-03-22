import sys


def main():
    if len(sys.argv) < 2:
        print("사용법: python3 add.py <숫자1> <숫자2> ...")
        sys.exit(1)

    try:
        numbers = [float(arg) for arg in sys.argv[1:]]
    except ValueError as e:
        print(f"오류: 유효한 숫자를 입력해주세요. ({e})")
        sys.exit(1)

    result = sum(numbers)
    print(int(result) if result == int(result) else result)


if __name__ == "__main__":
    main()
