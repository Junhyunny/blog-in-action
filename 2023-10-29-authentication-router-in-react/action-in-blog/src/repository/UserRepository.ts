import { User } from "../type/User";

const X_USER_TOKEN = "X-USER-TOKEN";

export const setAccessToken = (accessToken: string) => {
  localStorage.setItem(X_USER_TOKEN, accessToken);
};

export const getAccessToken = () => {
  return localStorage.getItem(X_USER_TOKEN);
};

export const hasToken = () => {
  return getAccessToken() !== null;
};

export const getUserInfo = (): User | null => {
  const token = getAccessToken();
  if (token) {
    const base64Url = token.split(".")[1];
    const base64 = base64Url.replace(/-/g, "+").replace(/_/g, "/");
    const jsonPayload = decodeURIComponent(
      window
        .atob(base64)
        .split("")
        .map(function (c) {
          return "%" + ("00" + c.charCodeAt(0).toString(16)).slice(-2);
        })
        .join(""),
    );
    const parsedJson = JSON.parse(jsonPayload);
    return {
      id: parsedJson.sub,
      name: parsedJson.name,
      roles: parsedJson.roles,
    };
  }
  return null;
};
