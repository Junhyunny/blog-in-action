import { useState } from "react";
import { act, fireEvent, render, screen } from "@testing-library/react";

import { clearAndType } from "../mock/util";

import DateInput from "./DateInput";

describe("DateInput Test", () => {
  test("비정상적인 IP를 입력 후 포커스 아웃하면 에러 메세지가 보인다", async () => {
    await act(async () =>
      render(
        <DateInput
          ariaLabel="date-input"
          value="1990-02-30"
          onValueChange={jest.fn()}
        />
      )
    );

    fireEvent.blur(textBox());

    expect(
      screen.getByText("유효한 날짜 타입이 아닙니다.")
    ).toBeInTheDocument();
  });

  test("비정상적인 IP를 입력한 상태에서 정상적인 IP로 변경하면 에러 메세지가 사라진다.", async () => {
    await act(async () => render(<WrappedDateInput value="1990-02-30" />));

    await act(async () => await clearAndType(textBox(), "1990-02-28"));
    fireEvent.blur(textBox());

    expect(
      screen.queryByText("유효한 날짜 타입이 아닙니다.")
    ).not.toBeInTheDocument();
  });
});

const WrappedDateInput = (props: any) => {
  const [value, setValue] = useState<string>(props.value);
  return (
    <DateInput ariaLabel="date-input" value={value} onValueChange={setValue} />
  );
};

const textBox = () => screen.getByLabelText("date-input");
