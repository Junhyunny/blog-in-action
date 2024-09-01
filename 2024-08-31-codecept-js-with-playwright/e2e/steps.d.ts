/// <reference types='codeceptjs' />
type steps_file = typeof import("./steps_file");

declare namespace CodeceptJS {
  type User = "JUNHYUNNY";

  interface SupportObject {
    I: I;
    current: any;
    loginAs: (user: User) => void;
  }
  interface Methods extends Playwright {}
  interface I extends ReturnType<steps_file> {}
  namespace Translation {
    interface Actions {}
  }
}
