export const loadImageWithRety = async (
  url: string,
  timeout: number = 1000,
  retry: number = 4,
): Promise<HTMLImageElement> => {
  return new Promise((resolve, reject) => {
    const image = new Image();
    image.onload = () => {
      resolve(image);
    };
    image.onerror = () => {
      setTimeout(() => {
        if (retry > 0) {
          loadImageWithRety(url, timeout, retry - 1)
            .then(resolve)
            .catch(reject);
        } else {
          reject(new Error("over maximum retry"));
        }
      }, timeout);
    };
    image.src = url;
  });
};
