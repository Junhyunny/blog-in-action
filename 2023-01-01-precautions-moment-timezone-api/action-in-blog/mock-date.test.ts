import moment from "moment-timezone";

describe("moment timezone tests", () => {
  beforeEach(() => {
    const now = Date.now();
    jest.spyOn(Date, "now").mockReturnValue(now);
  });

  it("mock now method in Date class", () => {
    const firstValue = moment.now();

    for (let index = 0; index < 10000000; index++) {}

    const secondValue = moment.now();
    expect(firstValue).toEqual(secondValue);
  });
});
