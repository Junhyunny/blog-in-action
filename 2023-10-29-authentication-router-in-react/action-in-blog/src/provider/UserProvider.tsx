import { createContext, ReactNode, useState } from "react";
import { User } from "../type/User";
import { getUserInfo, hasToken } from "../repository/UserRepository";

type UserContextType = {
  isLoggedIn: boolean;
  user: User | null;
};

type Props = {
  children: ReactNode;
};

export const UserContext = createContext<UserContextType | null>(null);

const UserProvider = ({ children }: Props) => {
  const [isLoggedIn, setLoggedIn] = useState<boolean>(hasToken());
  const [user, setUser] = useState<User | null>(getUserInfo());
  return (
    <UserContext.Provider
      value={{
        isLoggedIn,
        user,
      }}
    >
      {children}
    </UserContext.Provider>
  );
};

export default UserProvider;
