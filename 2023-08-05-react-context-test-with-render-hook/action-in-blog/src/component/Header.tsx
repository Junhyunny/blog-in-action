import { useContext } from "react";
import { UserContext } from "../context/UserContextProvider";

const Header = () => {
  const user = useContext(UserContext)!;
  return (
    <div>
      <div>{user.name}</div>
      <div>{user.email}</div>
    </div>
  );
};

export default Header;
