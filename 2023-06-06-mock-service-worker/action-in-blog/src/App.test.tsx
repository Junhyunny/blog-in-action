import React from "react";
import App from "./App";

import { render, screen } from "@testing-library/react";
import userEvent from "@testing-library/user-event";

test("renders todo list", async () => {
  render(<App />);

  expect(await screen.findByText("Frontend Study")).toBeInTheDocument();
  expect(screen.getByText("Backend Study")).toBeInTheDocument();
});

test("add new todo", async () => {
  render(<App />);

  await userEvent.type(screen.getByPlaceholderText("NEW TODO"), "DevOps Study");
  await userEvent.click(screen.getByText("ADD"));

  expect(await screen.findByText("DevOps Study")).toBeInTheDocument();
  expect(screen.getByPlaceholderText("NEW TODO")).toHaveValue("");
});
