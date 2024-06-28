import { throttle } from "./throttle";

test("when occur 100 times event with throttle in 500ms then invoke 5 times", () => {
  const spy = jest.fn(); // 1
  const sut = throttle(spy, 100);

  jest.useFakeTimers(); // 2
  for (let index = 0; index < 100; index++) {
    sut();
    jest.advanceTimersByTime(5);
  }

  expect(spy).toHaveBeenCalledTimes(5); // 3
});

test("when occur 100 times event with throttle in 500ms then invoke 100 times", () => {
  const spy = jest.fn(); // 1

  jest.useFakeTimers(); // 2
  for (let index = 0; index < 100; index++) {
    spy();
    jest.advanceTimersByTime(5);
  }

  expect(spy).toHaveBeenCalledTimes(100); // 3
});
