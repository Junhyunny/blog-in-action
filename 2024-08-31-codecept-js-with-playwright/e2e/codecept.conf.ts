import { setHeadlessWhen, setCommonPlugins } from "@codeceptjs/configure";
// turn on headless mode when running with HEADLESS=true environment variable
// export HEADLESS=true && npx codeceptjs run
setHeadlessWhen(process.env.HEADLESS);

// enable all common plugins https://github.com/codeceptjs/configure#setcommonplugins
setCommonPlugins();

export const config: CodeceptJS.MainConfig = {
  tests: "./*_test.ts",
  output: "./output",
  helpers: {
    Playwright: {
      browser: "chromium",
      url: "http://localhost:8080",
      show: true,
    },
  },
  include: {
    I: "./steps_file",
  },
  name: "e2e",
  plugins: {
    autoLogin: {
      enabled: true,
      saveToFile: true,
      inject: "loginAs",
      users: {
        JUNHYUNNY: {
          login: (I: CodeceptJS.I) => {
            I.clearCookie();
            I.login(secret("junhyunny"), secret("12345"));
          },
          check: (I) => {
            I.amOnPage("/");
            I.see("Hello World");
          },
        },
      },
    },
  },
};
