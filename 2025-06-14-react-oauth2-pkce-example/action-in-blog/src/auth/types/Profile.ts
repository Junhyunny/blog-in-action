export type Profile = {
  aud: string;
  "cognito:username": string;
  email: string;
  email_verified: boolean;
  exp: number;
  iat: number;
  iss: string;
  origin_jti: string;
  sub: string;
  token_use: string;
};
