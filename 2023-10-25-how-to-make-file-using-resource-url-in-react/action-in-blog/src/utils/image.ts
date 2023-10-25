export const loadImage = (url: string): Promise<Blob> => {
  return fetch(url).then((response) => response.blob());
};
