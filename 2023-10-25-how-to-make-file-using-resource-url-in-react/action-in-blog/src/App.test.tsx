import React from "react";
import userEvent from "@testing-library/user-event";
import { render, screen, waitFor } from "@testing-library/react";

import * as imageUtil from "./utils/image";

import App from "./App";

test("when click image then image loaded and next button enabled", async () => {
  jest
    .spyOn(imageUtil, "loadImage")
    .mockResolvedValue(new Blob(["image-binary"], { type: "image/jpeg" }));
  render(<App />);
  const baseImage = screen.getByAltText("base-img");

  userEvent.click(baseImage);

  await waitFor(() => {
    expect(baseImage).toHaveAttribute(
      "src",
      `data:image/jpeg;base64,${btoa("image-binary")}`,
    );
    expect(screen.getByRole("button", { name: "next" })).toBeEnabled();
  });
});
