export type AuthConfig = {
  clientId: string;
  redirectUri: string;
  scope: string;
  responseType: string;
  codeVerifier: string;
  state: string;
};
