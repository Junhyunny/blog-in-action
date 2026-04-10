import { render, screen, waitFor } from "@testing-library/react";
import { describe, expect, test } from "vitest";
import * as utils from "../utils/utils";
import { SkeletonImage } from "./SkeletonImage";

describe("SkeletonImage", () => {
  test("given loading is not finished when render SkeletonImage then see skelecto area", async () => {
    render(<SkeletonImage src="test-url" />);

    await waitFor(() => {
      expect(screen.getByLabelText("skeleton")).toBeInTheDocument();
      expect(screen.queryByRole("img")).not.toBeInTheDocument();
    });
  });

  test("given when loading is done when render SkeletonImage then see image element", async () => {
    vi.spyOn(utils, "loadImageWithRety").mockResolvedValue(new Image());

    render(<SkeletonImage src="test-url" />);

    expect(await screen.findByAltText("thumbnail-image")).toBeInTheDocument();
    expect(screen.queryByLabelText("alternate-image")).not.toBeInTheDocument();
    expect(screen.queryByLabelText("skeleton")).not.toBeInTheDocument();
  });

  test("given when loading has failed when render SkeletonImage then see alternate image element", async () => {
    vi.spyOn(utils, "loadImageWithRety").mockRejectedValue(
      new Error("Failed to load image"),
    );

    render(<SkeletonImage src="test-url" />);

    expect(await screen.findByLabelText("alternate-image")).toBeInTheDocument();
    expect(screen.queryByLabelText("thumbnail-image")).not.toBeInTheDocument();
    expect(screen.queryByLabelText("skeleton")).not.toBeInTheDocument();
  });
});
