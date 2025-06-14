import type { AuthConfig } from "../types/AuthConfig";
import type { TokenResponse } from "../types/TokenResponse.ts";
import type { User } from "../types/User.ts";
import type { Profile } from "../types/Profile.ts";
import type { CustomAuthContextProps } from "../CustomAuthProvider.tsx";
import { decodeJwt } from "jose";
import { generateCodeVerifier, generateOAuthState } from "./generator.ts";

export function clearAuthConfig() {
  const length = sessionStorage.length;
  for (let index = 0; index < length; index++) {
    const key = sessionStorage.key(index);
    if (key && key.includes("AuthConfig$$")) {
      sessionStorage.removeItem(key);
    }
  }
}

export function popAuthConfig(state: string): AuthConfig | undefined {
  const authConfig = sessionStorage.getItem(`AuthConfig$$${state}`);
  if (!authConfig) {
    return;
  }
  sessionStorage.removeItem(`AuthConfig$$${state}`);
  return JSON.parse(authConfig) as AuthConfig;
}

export function removeUserFromStorage(authority: string, clientId: string) {
  localStorage.removeItem(`openid.${authority}.${clientId}`);
}

export function saveAuthConfig({
  client_id,
  redirect_uri,
  scope,
  response_type,
}: Omit<CustomAuthContextProps, "children" | "authority">): AuthConfig {
  const state = generateOAuthState();
  const authConfig: AuthConfig = {
    clientId: client_id,
    redirectUri: redirect_uri,
    scope: scope,
    responseType: response_type,
    codeVerifier: generateCodeVerifier(),
    state: state,
  };
  sessionStorage.setItem(`AuthConfig$$${state}`, JSON.stringify(authConfig));
  return authConfig;
}

export function saveUser(
  authority: string,
  clientId: string,
  token: TokenResponse,
) {
  const user: User = {
    ...token,
    profile: decodeJwt(token.id_token) as Profile,
  };
  localStorage.setItem(`openid.${authority}.${clientId}`, JSON.stringify(user));
  return user;
}

export function getUser(authority: string, clientId: string) {
  const user = localStorage.getItem(`openid.${authority}.${clientId}`);
  if (!user) {
    return;
  }
  return JSON.parse(user) as User;
}
