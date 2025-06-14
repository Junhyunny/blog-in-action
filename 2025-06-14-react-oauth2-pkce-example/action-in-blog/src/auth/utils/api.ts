import type { TokenResponse } from "../types/TokenResponse.ts";
import type { OpenIdConfig } from "../types/OpenIdConfig.ts";

export const fetchAccessToken = async (
  endpointUri: string,
  formBody: string,
): Promise<TokenResponse> => {
  return await fetch(`${endpointUri}`, {
    method: "POST",
    headers: { "Content-Type": "application/x-www-form-urlencoded" },
    body: formBody,
  }).then((res) => res.json());
};

export const fetchOpenIdConfig = async (
  authority: string,
): Promise<OpenIdConfig> => {
  return await fetch(`${authority}/.well-known/openid-configuration`, {
    method: "GET",
  }).then((res) => res.json());
};
