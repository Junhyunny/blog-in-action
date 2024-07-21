import { render, screen, waitFor } from "@testing-library/react";
import App from "./App";
import userEvent from "@testing-library/user-event";

describe("App", () => {
  it("renders App", () => {
    render(<App />);

    expect(screen.getByText("Hello Vite + React!")).toBeInTheDocument();
  });

  it("click count button", async () => {
    render(<App />);

    userEvent.click(screen.getByText(/count is: /i));

    await waitFor(() => {
      expect(screen.getByText("count is: 1")).toBeInTheDocument();
    });
  });
});
