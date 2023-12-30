import userEvent from "@testing-library/user-event";

export const clearAndType = async (element: Element, value: string) => {
    await userEvent.clear(element);
    await userEvent.type(element, value);
};
