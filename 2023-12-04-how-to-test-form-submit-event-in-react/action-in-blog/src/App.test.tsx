import React from "react";
import { fireEvent, render, screen } from "@testing-library/react";
import userEvent from "@testing-library/user-event";

import App from "./App";

test("renders learn react link", () => {
  render(<App />);
  const form = loginForm();
  form.onsubmit = jest.fn();

  userEvent.type(nameInput(), "junhyunny");
  userEvent.type(passwordInput(), "12345");
  fireEvent.submit(loginButton());

  expect(form).toHaveAttribute("method", "post");
  expect(form).toHaveAttribute("action", "/login");
  expect(form).toHaveFormValues({
    username: "junhyunny",
    password: "12345",
  });
  expect(form.onsubmit).toHaveBeenCalled();
});

const loginForm = () =>
  screen.getByRole("form", {
    name: "form",
  });

const nameInput = () => screen.getByPlaceholderText("username");

const passwordInput = () => screen.getByPlaceholderText("password");

const loginButton = () =>
  screen.getByRole("button", {
    name: "login",
  });
