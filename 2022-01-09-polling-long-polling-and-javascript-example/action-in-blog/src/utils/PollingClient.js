export const timeoutPolling = (func, timeout) => {
  setTimeout(() => {
    func();
    timeoutPolling(func, timeout);
  }, timeout);
};

export const intervalPolling = (func, interval, maxAttempts = -1) => {
  let attempts = 0;
  let intervalId = setInterval(() => {
    if (maxAttempts === attempts) {
      clearInterval(intervalId);
      return;
    }
    attempts++;
    func();
  }, interval);
};

const sleep = (timeout = 100) => {
  return new Promise((resolve) => {
    setTimeout(resolve, timeout);
  });
};

export const sleepPolling = async (func, validateFunc, timeout) => {
  let result = await func();
  while (!validateFunc(result)) {
    await sleep(timeout);
    try {
      result = await func();
    } catch (e) {
      console.log(e.message);
    }
  }
  return result;
};

export const longPolling = async (func, validateFunc) => {
  try {
    let response = await func();
    if (validateFunc(response)) {
      return response;
    }
  } catch (error) {
    console.log(error.message);
  }
  return await longPolling(func, validateFunc);
};
