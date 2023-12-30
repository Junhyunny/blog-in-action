import * as sut from "./validate";

describe("validate Tests", () => {
  test.each([
    ["3", true],
    ["03", true],
    [" 3", true],
    ["a", false],
    ["!", false],
    ["#a3", false],
    ["!3", false],
  ])("%s 값이 숫자인지 확인한다.", (value, expectedResult) => {
    expect(expectedResult).toEqual(sut.isNumberValue(value));
  });

  test.each([
    ["Y", true],
    ["y", true],
    ["N", true],
    ["n", true],
    [" !", false],
    ["N ", false],
    ["n ", false],
    [" n", false],
    [" N", false],
    [" Y", false],
    [" Y", false],
    ["y ", false],
    ["Y ", false],
  ])("%s 값이 Y 혹은 N인지 확인한다.", (value, expectedResult) => {
    expect(expectedResult).toEqual(sut.isUseYn(value));
  });

  test.each([
    ["", true],
    ["192.168.1.2", true],
    ["192.168.100.255", true],
    ["12.18.10.25", true],
    ["0.0.0.0", true],
    ["255.255.255.255", true],
    ["255.255.255.255/0", true],
    ["255.255.255.255/1", true],
    ["255.255.255.255/22", true],
    ["255.255.255.255/32", true],
    ["255.255.255.", false],
    ["255.255..255", false],
    ["255..255.255", false],
    [".255.255.255", false],
    ["256.0.1.2", false],
    ["255.0.1.-2", false],
    ["255.05.1.5", false],
    ["255.05.1.5", false],
    ["255.5.01.5", false],
    ["255.5.1.05", false],
    ["255.50.00.5", false],
    ["255.255.255.255/", false],
    ["255.255.255.255/a", false],
    ["255.255.255.255/c0", false],
    ["255.255.255.255/2s", false],
  ])("% IP 값의 포맷이 정상인지 확인한다.", (value, expectedResult) => {
    expect(expectedResult).toEqual(sut.isIpAddress(value));
  });

  test.each([
    ["", true],
    ["02-321-3201", true],
    ["031-1321-3201", true],
    ["041-9219-3201", true],
    ["010-2321-3201", true],
    ["011-321-3201", true],
    ["016-321-3201", true],
    ["017-321-9999", true],
    ["2999-3123-9999", false],
    ["-3123-9999", false],
    ["02-9999", false],
    ["02-35-9999", false],
    ["02-353-999", false],
    ["02-31123-9999", false],
    ["02-3123-99991", false],
  ])("% 연락처 값의 포맷이 정상인지 확인한다.", (value, expectedResult) => {
    expect(expectedResult).toEqual(sut.isPhoneContact(value));
  });

  test.each([
    ["", true],
    ["ezmeta@gmail.com", true],
    ["ezmeta@gmail.com", true],
    ["ezmetaa@yahoo.co.kr", true],
    ["ezmet33a@korail.co.kr", true],
    ["e13zmet11a@snu.ac.kr", true],
    ["ezmeta192@skku.ac.kr", true],
    ["ezmeta.ak@skku.ac.kr", true],
    ["21icxzc@asddd.io", true],
    ["017-321@9999.com", true],
    ["2999.3123@9999.com", true],
    ["010-2321-3201", false],
    ["011@321-3201", false],
    ["ezmeta@.com", false],
    ["016-321@3201", false],
    ["askii123 naver.com", false],
    ["gmail.com", false],
    ["naver.com", false],
    ["21icxzc@@asddd.io", false],
  ])("%s 이메일 값의 포맷이 정상인지 확인한다.", (value, expectedResult) => {
    expect(expectedResult).toEqual(sut.isEmail(value));
  });

  test.each([
    ["2023-01-20", true],
    ["9999-01-20", true],
    ["1990-02-28", true],
    ["1990-12-30", true],
    ["1990-12-31", true],
    ["1990-07-31", true],
    ["1990-08-31", true],
    ["2024-02-29", true],
    ["1990-01-32", false],
    ["1990-01-1", false],
    ["1990-1-01", false],
    ["1990/01/01", false],
    ["01/01/1990", false],
    ["1990-02-30", false],
    ["1990-02-31", false],
    ["1990-06-31", false],
    ["1990-05-32", false],
    ["2023-02-29", false],
  ])("%s 날짜 값의 포맷이 정상인지 확인한다.", (value, expectedResult) => {
    expect(expectedResult).toEqual(sut.isDate(value));
  });
});
