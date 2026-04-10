import { loadImageWithRety } from "./utils";

const mockImage = {
  onload: null,
  onerror: null,
  src: "",
} as unknown as HTMLImageElement;

beforeEach(() => {
  vi.stubGlobal(
    "Image",
    vi.fn(function (this: HTMLImageElement) {
      return mockImage;
    }),
  );
  vi.stubGlobal("fetch", vi.fn());
});

afterEach(() => {
  vi.unstubAllGlobals();
});

describe("loadImageWithRetry", () => {
  beforeEach(() => {
    vi.useFakeTimers();
  });

  afterEach(() => {
    vi.useRealTimers();
  });

  test("image src is set correctly", async () => {
    const resultPromise = loadImageWithRety("https://example.com/temp.png");

    mockImage?.onload?.(new Event("load"));

    const result = await resultPromise;
    expect(result.src).toEqual("https://example.com/temp.png");
  });

  test("fetch is called with no-cache on error", async () => {
    const url = "https://example.com/temp.png";
    const resultPromise = loadImageWithRety(url, 100, 1);

    mockImage?.onerror?.(new Event("error"));
    expect(fetch).toHaveBeenCalledWith(url, { cache: "no-cache" });

    vi.advanceTimersByTime(100);
    mockImage?.onload?.(new Event("load"));

    await resultPromise;
  });

  test("retries on error and eventually resolves", async () => {
    const url = "https://example.com/temp.png";
    const resultPromise = loadImageWithRety(url, 100, 2);

    mockImage?.onerror?.(new Event("error"));
    vi.advanceTimersByTime(100);
    mockImage?.onerror?.(new Event("error"));
    vi.advanceTimersByTime(100);
    mockImage?.onload?.(new Event("load"));

    const result = await resultPromise;
    expect(result.src).toEqual(url);
    expect(fetch).toHaveBeenCalledTimes(2);
    expect(fetch).toHaveBeenCalledWith(url, { cache: "no-cache" });
  });

  test("over max retries then rejects", async () => {
    const url = "https://example.com/temp.png";
    const resultPromise = loadImageWithRety(url, 100, 2);

    mockImage?.onerror?.(new Event("error"));
    vi.advanceTimersByTime(100);
    mockImage?.onerror?.(new Event("error"));
    vi.advanceTimersByTime(100);
    mockImage?.onerror?.(new Event("error"));
    vi.advanceTimersByTime(100);

    await expect(resultPromise).rejects.toThrow("over maximum retry");
    expect(fetch).toHaveBeenCalledTimes(3);
    expect(fetch).toHaveBeenCalledWith(url, { cache: "no-cache" });
  });
});
