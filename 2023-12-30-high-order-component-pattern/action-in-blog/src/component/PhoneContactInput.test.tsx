import { useState } from "react";
import { act, fireEvent, render, screen } from "@testing-library/react";

import { clearAndType } from "../mock/util";

import PhoneContactInput from "./PhoneContactInput";

describe("PhoneContactInput Test", () => {
  test("비정상적인 연락처를 입력 후 포커스 아웃하면 에러 메세지가 보인다", async () => {
    await act(async () =>
      render(
        <PhoneContactInput
          ariaLabel="phone-contact-input"
          value="02-35-9999"
          onValueChange={jest.fn()}
        />
      )
    );

    fireEvent.blur(textBox());

    expect(
      screen.getByText("유효한 연락처 형식이 아닙니다.")
    ).toBeInTheDocument();
  });

  test("비정상적인 연락처를 입력한 상태에서 정상적인 IP로 변경하면 에러 메세지가 사라진다.", async () => {
    await act(async () =>
      render(<WrappedPhoneContactInput value="02-35-9999" />)
    );

    await act(async () => await clearAndType(textBox(), "02-321-3201"));
    fireEvent.blur(textBox());

    expect(
      screen.queryByText("유효한 연락처 형식이 아닙니다.")
    ).not.toBeInTheDocument();
  });
});

const WrappedPhoneContactInput = (props: any) => {
  const [value, setValue] = useState<string>(props.value);
  return (
    <PhoneContactInput
      ariaLabel="phone-contact-input"
      value={value}
      onValueChange={setValue}
    />
  );
};

const textBox = () => screen.getByLabelText("phone-contact-input");
