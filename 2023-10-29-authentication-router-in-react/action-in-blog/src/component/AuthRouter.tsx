import { useContext } from "react";
import { Navigate, Outlet } from "react-router-dom";

import { UserContext } from "../provider/UserProvider";

type Props = {
  isAdminOnly?: boolean;
};

const AuthRouter = ({ isAdminOnly }: Props = { isAdminOnly: false }) => {
  const { isLoggedIn, user } = useContext(UserContext)!;
  if (!isLoggedIn) {
    return <Navigate replace to="/home" />;
  }
  if (!isAdminOnly) {
    return <Outlet />;
  }
  return user?.roles.includes("ADMIN") ? (
    <Outlet />
  ) : (
    <Navigate replace to="/home" />
  );
};

export default AuthRouter;
