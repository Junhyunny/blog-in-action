import { createContext, ReactNode, useEffect, useState } from "react";
import axios from "axios";

interface User {
  id: number;
  name: string;
  email: string;
}

const initialState: User = {
  id: 0,
  name: "",
  email: "",
};

export const UserContext = createContext<User | null>(null);

const UserContextProvider = ({ children }: { children: ReactNode }) => {
  const [user, setUser] = useState<User>(initialState);

  useEffect(() => {
    axios
      .get("/users", {
        headers: {
          Authorization: "some-token",
        },
      })
      .then((response) => {
        const { data } = response;
        setUser(data);
      });
  }, []);

  return <UserContext.Provider value={user}>{children}</UserContext.Provider>;
};

export default UserContextProvider;
