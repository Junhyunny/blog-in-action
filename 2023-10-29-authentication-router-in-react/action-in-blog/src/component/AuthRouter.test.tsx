import { ReactNode } from "react";
import { render, screen } from "@testing-library/react";

import UserProvider from "../provider/UserProvider";
import AuthRouter from "./AuthRouter";
import { setAccessToken } from "../repository/UserRepository";

jest.mock("react-router-dom", () => ({
  ...jest.requireActual("react-router-dom"),
  Outlet: () => <div data-testid="Outlet" />,
  Navigate: (props: any) => <div data-testid="Navigate">{props.to}</div>,
}));

const stubToken =
  "header.eyJzdWIiOiJqdW5oeXVubnkiLCJyb2xlcyI6WyJVU0VSIl0sImlhdCI6MTUxNjIzOTAyMiwiZXhwIjoxNjk4NTAwMDUxfQ.signature";

const stubAdminToken =
  "header.eyJzdWIiOiJqdW5oeXVubnkiLCJyb2xlcyI6WyJBRE1JTiJdLCJpYXQiOjE1MTYyMzkwMjIsImV4cCI6MTY5ODUwMDA1MX0.signature";

describe("AuthRouter Tests", () => {
  test("given user is not login when render AuthRouter then return Navigate with home path", () => {
    render(withContext(<AuthRouter />));

    expect(screen.getByTestId("Navigate")).toBeInTheDocument();
    expect(screen.getByText("/home")).toBeInTheDocument();
  });

  test("given user is login when render AuthRouter then return Outlet", () => {
    setAccessToken(stubToken);

    render(withContext(<AuthRouter />));

    expect(screen.getByTestId("Outlet")).toBeInTheDocument();
  });

  test("given user is login but not admin when render AuthRouter when isOnlyAdmin is true then Navigate with home path", () => {
    setAccessToken(stubToken);

    render(withContext(<AuthRouter isAdminOnly={true} />));

    expect(screen.getByTestId("Navigate")).toBeInTheDocument();
    expect(screen.getByText("/home")).toBeInTheDocument();
  });

  test("given admin is login when render AuthRouter when isOnlyAdmin is true then Outlet", () => {
    setAccessToken(stubAdminToken);

    render(withContext(<AuthRouter isAdminOnly={true} />));

    expect(screen.getByTestId("Outlet")).toBeInTheDocument();
  });
});

const withContext = (component: ReactNode) => {
  return <UserProvider>{component}</UserProvider>;
};
