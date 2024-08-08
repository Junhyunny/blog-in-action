import { beforeEach, describe, expect, test, vi } from "vitest";
import { render, screen, waitFor } from "@testing-library/react";
import App from "./App.tsx";
import axios from "axios";
import userEvent from "@testing-library/user-event";

describe("App tests", () => {
  beforeEach(() => {
    vi.spyOn(axios, "get").mockResolvedValue({
      data: [],
    });
  });

  test("when render App then see screen", () => {
    render(<App />);

    expect(screen.getByPlaceholderText("add new todo...")).toBeInTheDocument();
    expect(screen.getByRole("button", { name: "submit" }));
  });

  test("given fetch data from server when render App then see todo list", async () => {
    const spyAxiosGet = vi.spyOn(axios, "get").mockResolvedValue({
      data: [
        { id: 1, title: "Hello" },
        { id: 2, title: "World" },
      ],
    });

    render(<App />);

    await waitFor(() => {
      expect(screen.getByText("Hello")).toBeInTheDocument();
      expect(screen.getByText("World")).toBeInTheDocument();
      expect(spyAxiosGet).toHaveBeenCalledWith("/api/todos");
    });
  });

  test("given typing new todo when click submit then axios post is called", async () => {
    const spyAxiosPost = vi.spyOn(axios, "post").mockResolvedValue(undefined);
    render(<App />);
    await userEvent.type(
      screen.getByPlaceholderText("add new todo..."),
      "Hello World",
    );

    await userEvent.click(screen.getByRole("button", { name: "submit" }));

    expect(screen.getByPlaceholderText("add new todo...")).toHaveValue("");
    expect(spyAxiosPost).toHaveBeenCalledWith("/api/todos", {
      title: "Hello World",
    });
  });
});
