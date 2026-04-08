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

  test("retries on error and eventually resolves", async () => {
    const resultPromise = loadImageWithRety(
      "https://example.com/temp.png",
      100,
      2,
    );

    mockImage?.onerror?.(new Event("error"));
    vi.advanceTimersByTime(100);
    mockImage?.onerror?.(new Event("error"));
    vi.advanceTimersByTime(100);
    mockImage?.onload?.(new Event("load"));

    const result = await resultPromise;
    expect(result.src).toEqual("https://example.com/temp.png");
  });

  test("over max retries then rejects", async () => {
    const resultPromise = loadImageWithRety(
      "https://example.com/temp.png",
      100,
      2,
    );

    mockImage?.onerror?.(new Event("error"));
    vi.advanceTimersByTime(100);
    mockImage?.onerror?.(new Event("error"));
    vi.advanceTimersByTime(100);
    mockImage?.onerror?.(new Event("error"));
    vi.advanceTimersByTime(100);

    await expect(resultPromise).rejects.toThrow("over maximum retry");
  });
});
