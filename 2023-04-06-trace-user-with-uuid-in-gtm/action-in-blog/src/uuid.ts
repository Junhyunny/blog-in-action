const getUUID = () => {
  return "xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx".replace(/[xy]/g, function (c) {
    const r = (Math.random() * 16) | 0,
      v = c === "x" ? r : (r & 3) | 8;
    return v.toString(16);
  });
};

export const getUserTransactionId = () => {
  let txId = localStorage.getItem("TX_ID");
  if (txId === null) {
    txId = getUUID();
    localStorage.setItem("TX_ID", txId);
  }
  return txId;
};
