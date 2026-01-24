import { defineConfig, devices } from "@playwright/test";

export default defineConfig({
  testDir: "./tests",
  fullyParallel: true,
  forbidOnly: !!process.env.CI,
  retries: process.env.CI ? 2 : 0,
  workers: process.env.CI ? 1 : undefined,
  reporter: [["html", { open: "never" }]],
  use: {
    // 권한 허용
    permissions: ["camera"],
    baseURL: "http://localhost:5173",
    trace: "on-first-retry",
    headless: !!process.env.CI,
  },
  projects: [
    {
      name: "chromium",
      use: {
        ...devices["Desktop Chrome"],
        // 크롬 실행 옵션
        launchOptions: {
          args: [
            "--use-fake-device-for-media-stream",
            "--use-file-for-fake-video-capture=media/output.y4m",
          ],
        },
      },
    },
  ],
});
