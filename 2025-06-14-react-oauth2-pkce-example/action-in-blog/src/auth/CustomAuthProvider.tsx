import {
  createContext,
  type ReactNode,
  useContext,
  useEffect,
  useState,
} from "react";
import {
  generateCodeChallenge,
  generateQueryParams,
} from "./utils/generator.ts";
import type { RedirectUrlConfig } from "./types/RedirectUrlConfig.ts";
import type { User } from "./types/User.ts";
import type { AuthorizationCode } from "./types/AuthorizationCode.ts";
import { fetchAccessToken, fetchOpenIdConfig } from "./utils/api.ts";
import {
  clearAuthConfig,
  getUser,
  popAuthConfig,
  removeUserFromStorage,
  saveAuthConfig,
  saveUser,
} from "./utils/storage.ts";

export const useCustomAuth = () => {
  return useContext(Context)!;
};

type CustomAuthContext = {
  isLoading: boolean;
  isAuthenticated: boolean;
  user?: User;
  error?: Error;
  signinRedirect: () => void;
  removeUser: () => void;
};

const Context = createContext<CustomAuthContext | null>(null);

export type CustomAuthContextProps = {
  children: ReactNode;
  authority: string;
  client_id: string;
  redirect_uri: string;
  response_type: string;
  scope: string;
};

const CustomAuthProvider = ({
  children,
  authority,
  client_id,
  redirect_uri,
  response_type,
  scope,
}: CustomAuthContextProps) => {
  const sessionUser = getUser(authority, client_id);
  const [isLoading, setIsLoading] = useState(false);
  const [isAuthenticated, setIsAuthenticated] = useState(
    sessionUser !== undefined,
  );
  const [user, setUser] = useState<User | undefined>(sessionUser);
  const [error, setError] = useState<Error>();

  const getAuthorizationCode = (): AuthorizationCode => {
    const params = new URLSearchParams(window.location.search);
    const code = params.get("code");
    const state = params.get("state");
    return {
      code: code,
      state: state,
    };
  };

  const authentication = async () => {
    const { code, state } = getAuthorizationCode();
    if (!code || !state) {
      clearAuthConfig();
      return;
    }
    const config = popAuthConfig(state);
    if (!config) {
      return;
    }
    setIsLoading(true);
    const { token_endpoint } = await fetchOpenIdConfig(authority);
    const data = {
      grant_type: "authorization_code",
      code,
      redirect_uri: config.redirectUri,
      client_id: config.clientId,
      code_verifier: config.codeVerifier,
    };
    const formBody = generateQueryParams(data);
    const tokenResponse = await fetchAccessToken(token_endpoint, formBody);
    const savedUser = saveUser(authority, config.clientId, tokenResponse);
    setIsAuthenticated(true);
    setUser(savedUser);
  };

  const signinRedirect = async () => {
    const { state, codeVerifier } = saveAuthConfig({
      client_id,
      redirect_uri,
      scope,
      response_type,
    });
    const codeChallenge = await generateCodeChallenge(codeVerifier);
    const { authorization_endpoint } = await fetchOpenIdConfig(authority);
    const config: RedirectUrlConfig = {
      client_id,
      code_challenge: codeChallenge,
      code_challenge_method: "S256",
      redirect_uri,
      response_type,
      scope,
      state,
    };
    window.location.href = `${authorization_endpoint}?${generateQueryParams(config)}`;
  };

  const removeUser = () => {
    setUser(undefined);
    setIsAuthenticated(false);
    removeUserFromStorage(authority, client_id);
  };

  useEffect(() => {
    authentication()
      .catch((err) => setError(err))
      .finally(() => setIsLoading(false));
  }, []);

  return (
    <Context.Provider
      value={{
        isLoading,
        isAuthenticated,
        user,
        error,
        signinRedirect,
        removeUser,
      }}
    >
      {children}
    </Context.Provider>
  );
};

export default CustomAuthProvider;
