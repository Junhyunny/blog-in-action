import { useState } from "react";
import { act, fireEvent, render, screen } from "@testing-library/react";

import { clearAndType } from "../mock/util";

import IpInput from "./IpInput";

describe("IpInput Test", () => {
  test("비정상적인 IP를 입력 후 포커스 아웃하면 에러 메세지가 보인다", async () => {
    await act(async () =>
      render(
        <IpInput
          ariaLabel="ip-input"
          value="192.25.255.256"
          onValueChange={jest.fn()}
        />
      )
    );

    fireEvent.blur(textBox());

    expect(screen.getByText("유효한 IP가 아닙니다.")).toBeInTheDocument();
  });

  test("비정상적인 IP를 입력한 상태에서 정상적인 IP로 변경하면 에러 메세지가 사라진다.", async () => {
    await act(async () => render(<WrappedIpInput value="192.25.255.256" />));

    await act(async () => await clearAndType(textBox(), "192.25.255.255"));
    fireEvent.blur(textBox());

    expect(screen.queryByText("유효한 IP가 아닙니다.")).not.toBeInTheDocument();
  });
});

const WrappedIpInput = (props: any) => {
  const [value, setValue] = useState<string>(props.value);
  return (
    <IpInput ariaLabel="ip-input" value={value} onValueChange={setValue} />
  );
};

const textBox = () => screen.getByLabelText("ip-input");
