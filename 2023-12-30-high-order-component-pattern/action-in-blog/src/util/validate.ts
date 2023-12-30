export const isNumberValue = (value: string) => {
  return !isNaN(+value);
};

export const isUseYn = (value: string) => {
  return value === "Y" || value === "y" || value === "N" || value === "n";
};

export const isIpAddress = (value: string) => {
  if (value === "") {
    return true;
  }
  return (
    /^((25[0-5]|(2[0-4]|1\d|[1-9]|)\d)\.?\b){4}$/.test(value) ||
    /^((25[0-5]|(2[0-4]|1\d|[1-9]|)\d)\.?\b){4}(\/([0-9]|[1-2][0-9]|3[0-2]))?$/.test(
      value
    )
  );
};

export const isPhoneContact = (value: string) => {
  if (value === "") {
    return true;
  }
  return /^\d{2,3}-\d{3,4}-\d{4}$/.test(value);
};

export const isEmail = (value: string) => {
  if (value === "") {
    return true;
  }
  return /^[\w-.]+@([\w-]+\.)+[\w-]{2,4}$/g.test(value);
};

export function isDate(value: string) {
  const isNan = isNaN(Date.parse(value));
  if (!isNan) {
    return new Date(value).toISOString().includes(value);
  }
  return false;
}
