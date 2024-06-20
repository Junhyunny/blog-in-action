import { render } from "@testing-library/react";

import * as eventListeners from "../utils/event-listeners";
import CustomButton from "./CustomButton";

describe("CustomButton", () => {
  it("when unmount custom button then remove event listener call", () => {
    const spyAddClickEvent = jest.spyOn(eventListeners, "addClickEvent");
    const spyRemoveClickEvent = jest.spyOn(eventListeners, "removeClickEvent");
    const { unmount } = render(<CustomButton />);

    unmount();

    expect(spyAddClickEvent).toHaveBeenNthCalledWith(1, expect.any(Function));
    expect(spyRemoveClickEvent).toHaveBeenCalledTimes(1, expect.any(Function));
    const addClickEventArg = spyAddClickEvent.mock.calls[0][0];
    const removeClickEventArg = spyRemoveClickEvent.mock.calls[0][0];
    expect(addClickEventArg).toEqual(removeClickEventArg);
  });
});
