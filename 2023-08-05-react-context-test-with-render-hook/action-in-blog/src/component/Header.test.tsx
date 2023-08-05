import { render, screen } from "@testing-library/react";

import UserContextProvider from "../context/UserContextProvider";
import Header from "./Header";
import axios from "axios";

it("사용자는 자신의 이름과 이메일 정보를 헤더에서 확인할 수 있다.", async () => {
  jest.spyOn(axios, "get").mockResolvedValue({
    data: {
      id: 1,
      name: "Junhyunny",
      email: "kang3966@naver.com",
    },
  });

  render(
    <UserContextProvider>
      <Header />
    </UserContextProvider>
  );

  expect(await screen.findByText("Junhyunny")).toBeInTheDocument();
  expect(screen.getByText("kang3966@naver.com")).toBeInTheDocument();
});
