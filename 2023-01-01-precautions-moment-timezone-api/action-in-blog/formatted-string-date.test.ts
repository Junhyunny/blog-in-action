import moment from "moment-timezone";

it("formatted string date test - wrong usage", () => {
  const dateTime = "2023-01-01 11:45:00";
  const timestamp = moment(dateTime).tz("Europe/London").valueOf();

  const result = moment(timestamp)
    .tz("Europe/London")
    .format("YYYY-MM-DD HH:mm:ss");

  expect(result).toEqual(dateTime);
});

it("formatted string date test - correct usage", () => {
  const dateTime = "2023-01-01 11:45:00";
  const timestamp = moment(dateTime).tz("Europe/London", true).valueOf();

  const result = moment(timestamp)
    .tz("Europe/London")
    .format("YYYY-MM-DD HH:mm:ss");

  expect(result).toEqual(dateTime);
});
