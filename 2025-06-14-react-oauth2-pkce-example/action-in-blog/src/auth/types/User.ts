import type { Profile } from "./Profile.ts";

export type User = {
  id_token: string;
  access_token: string;
  refresh_token: string;
  expires_in: number;
  token_type: string;
  profile: Profile;
};
