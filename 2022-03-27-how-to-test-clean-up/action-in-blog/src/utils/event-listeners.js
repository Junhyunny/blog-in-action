export const addClickEvent = (eventListener) => {
  window.addEventListener("click", eventListener);
};

export const removeClickEvent = (eventListener) => {
  window.removeEventListener("click", eventListener);
};
