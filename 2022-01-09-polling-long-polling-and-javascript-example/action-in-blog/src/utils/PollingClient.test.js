import {
  intervalPolling,
  longPolling,
  sleepPolling,
  timeoutPolling,
} from "./PollingClient";

describe("PollingClient test", () => {
  beforeEach(() => {
    jest.clearAllTimers();
    jest.useRealTimers();
    jest.restoreAllMocks();
  });

  test("when timeout 1 second for polling then call 6 times in 6 seconds", async () => {
    jest.useFakeTimers();
    const spyFunc = jest.fn();

    timeoutPolling(spyFunc, 1000);
    for (let i = 0; i < 6; i++) {
      jest.advanceTimersByTime(1000);
    }

    expect(spyFunc).toHaveBeenCalledTimes(6);
  });

  test("when 1 second interval and max attempt count is 5 for polling then call 5 times in 6 seconds", () => {
    jest.useFakeTimers(); // 1
    const spyFunc = jest.fn();

    intervalPolling(spyFunc, 1000, 5); // 2
    for (let i = 0; i < 6; i++) {
      jest.advanceTimersByTime(1000);
    }

    expect(spyFunc).toHaveBeenCalledTimes(5); // 3
  });

  test("second response is valid when polling then callback function is called 2 times", async () => {
    const mockCallback = jest
      .fn()
      .mockResolvedValueOnce({
        data: "Welcome",
      })
      .mockResolvedValueOnce({
        data: "Junhyunny",
      })
      .mockResolvedValueOnce({
        data: "Dev",
      })
      .mockResolvedValueOnce({
        data: "Log",
      });
    const validateFn = (result) => "Junhyunny" === result.data;

    const data = await sleepPolling(mockCallback, validateFn, 100);

    expect(mockCallback).toHaveBeenCalledTimes(2);
    expect(data).toEqual({
      data: "Junhyunny",
    });
  });

  test("third response is valid when polling then callback function is called 3 times", async () => {
    const mockCallback = jest
      .fn()
      .mockRejectedValueOnce({
        code: "ECONNABORTED",
      })
      .mockRejectedValueOnce({
        status: 500,
      })
      .mockResolvedValueOnce({
        data: "Junhyunny",
      })
      .mockResolvedValueOnce({
        data: "Tangerine",
      });
    const validateFn = (response) => "Junhyunny" === response.data;

    const data = await longPolling(mockCallback, validateFn, 100);

    expect(mockCallback).toHaveBeenCalledTimes(3);
    expect(data).toEqual({
      data: "Junhyunny",
    });
  });
});
