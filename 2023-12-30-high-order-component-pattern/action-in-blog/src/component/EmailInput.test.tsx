import { useState } from "react";
import { act, fireEvent, render, screen } from "@testing-library/react";

import { clearAndType } from "../mock/util";

import EmailInput from "./EmailInput";

describe("EmailInput Test", () => {
  test("비정상적인 IP를 입력 후 포커스 아웃하면 에러 메세지가 보인다", async () => {
    await act(async () =>
      render(
        <EmailInput
          ariaLabel="email-input"
          value="ezmeta@.com"
          onValueChange={jest.fn()}
        />
      )
    );

    fireEvent.blur(textBox());

    expect(screen.getByText("유효한 이메일이 아닙니다.")).toBeInTheDocument();
  });

  test("비정상적인 IP를 입력한 상태에서 정상적인 IP로 변경하면 에러 메세지가 사라진다.", async () => {
    await act(async () => render(<WrappedEmailInput value="ezmeta@.com" />));

    await act(async () => await clearAndType(textBox(), "ezmeta@gmail.com"));
    fireEvent.blur(textBox());

    expect(
      screen.queryByText("유효한 이메일이 아닙니다.")
    ).not.toBeInTheDocument();
  });
});

const WrappedEmailInput = (props: any) => {
  const [value, setValue] = useState<string>(props.value);
  return (
    <EmailInput
      ariaLabel="email-input"
      value={value}
      onValueChange={setValue}
    />
  );
};

const textBox = () => screen.getByLabelText("email-input");
