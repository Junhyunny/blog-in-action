import { useContext } from "react";
import { renderHook, waitFor } from "@testing-library/react";

import UserContextProvider, { UserContext } from "./UserContextProvider";
import axios from "axios";

it("사용자 컨텍스트는 사용자 정보를 제공한다.", async () => {
  jest.spyOn(axios, "get").mockResolvedValue({
    data: {
      id: 1,
      name: "Junhyunny",
      email: "kang3966@naver.com",
    },
  });
  const wrapper = (props: any) => (
    <UserContextProvider>{props.children}</UserContextProvider>
  );

  const { result } = renderHook(() => useContext(UserContext), {
    wrapper,
  });

  await waitFor(() => {
    expect(result.current?.id).toEqual(1);
    expect(result.current?.name).toEqual("Junhyunny");
    expect(result.current?.email).toEqual("kang3966@naver.com");
  });
});
