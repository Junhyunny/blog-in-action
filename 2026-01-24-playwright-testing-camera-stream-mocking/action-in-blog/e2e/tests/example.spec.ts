import { randomUUID } from "node:crypto";
import * as fs from "node:fs";
import { expect, test } from "@playwright/test";

test("download video file", async ({ page }) => {
  await page.goto("/");
  await page.waitForTimeout(500); // wait for media stream to be ready

  await page.getByRole("button", { name: "녹화 시작" }).click();
  await page.waitForTimeout(1000); // wait for recording

  const downloadPromise = page.waitForEvent("download");
  await page.getByRole("button", { name: "녹화 종료" }).click();

  const download = await downloadPromise;
  const filePath = `downloads/${randomUUID()}.webm`;
  await download.saveAs(filePath);
  expect(fs.existsSync(filePath)).toBeTruthy();
});

test("download image file", async ({ page }) => {
  await page.goto("/");
  await page.getByText("사진").click();
  await page.waitForTimeout(500); // wait for media stream to be ready

  const downloadPromise = page.waitForEvent("download");
  await page.getByRole("button", { name: "촬영" }).click();

  const download = await downloadPromise;
  const filePath = `downloads/${randomUUID()}.png`;
  await download.saveAs(filePath);
  expect(fs.existsSync(filePath)).toBeTruthy();
});
