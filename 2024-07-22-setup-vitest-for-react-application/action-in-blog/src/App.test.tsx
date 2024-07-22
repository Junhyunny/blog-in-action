import { describe, expect, test } from "vitest";
import { render, screen } from "@testing-library/react";
import userEvent from "@testing-library/user-event";

import App from "./App.tsx";

test("adds 1 + 2 to equal 3", () => {
  expect(1 + 2).toBe(3);
});

describe("App Tests", () => {
  test("render App Component", () => {
    render(<App />);

    expect(screen.getByPlaceholderText("username")).toBeInTheDocument();
    expect(screen.getByRole("button", { name: "reset" })).toBeInTheDocument();
  });

  test("write username", async () => {
    render(<App />);
    const input = screen.getByPlaceholderText("username");

    await userEvent.type(input, "junhyunny");

    expect(input).toHaveValue("junhyunny");
  });

  test("username is written when click reset then clear input field", async () => {
    render(<App />);
    const input = screen.getByPlaceholderText("username");
    const button = screen.getByRole("button", { name: "reset" });
    await userEvent.type(input, "junhyunny");

    await userEvent.click(button);

    expect(input).not.toHaveValue();
  });
});
