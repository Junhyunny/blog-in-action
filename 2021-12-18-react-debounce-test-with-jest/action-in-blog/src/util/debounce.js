export const debounce = (func, timeout) => {
  let timer;
  return (...args) => {
    const context = this;
    if (timer) {
      clearTimeout(timer);
    }
    timer = setTimeout(() => {
      func.apply(context, args);
    }, timeout);
  };
};
