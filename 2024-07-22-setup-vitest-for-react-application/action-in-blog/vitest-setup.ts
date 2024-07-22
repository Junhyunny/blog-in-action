import "@testing-library/jest-dom/vitest"; // 1
import { cleanup } from "@testing-library/react";
import { afterEach } from "vitest";

afterEach(() => {
  cleanup(); // 2
});
