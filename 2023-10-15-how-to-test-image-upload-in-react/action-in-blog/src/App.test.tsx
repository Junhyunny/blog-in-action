import userEvent from "@testing-library/user-event";
import { render, screen, waitFor } from "@testing-library/react";

import App from "./App";

test("when choose an image file then change image on screen", async () => {
  const file = new File(["some-image"], "profile.jpg", {
    type: "image/jpeg",
  });
  render(<App />);

  userEvent.upload(screen.getByLabelText("file-selector"), file);

  await waitFor(() =>
    expect(screen.getByAltText("profile-image")).toHaveAttribute(
      "src",
      `data:image/jpeg;base64,${btoa("some-image")}`,
    ),
  );
});
