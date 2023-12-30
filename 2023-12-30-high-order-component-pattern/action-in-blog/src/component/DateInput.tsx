import { isDate } from "../util/validate";
import { ValidationTextInput } from "./ValidationComponents";

type Props = {
  value: string;
  onValueChange: (value: string) => void;
  ariaLabel?: string;
  placeholder?: string;
};

const DateInput = ({ ariaLabel, value, onValueChange, placeholder }: Props) => {
  return (
    <ValidationTextInput
      value={value}
      onValueChange={onValueChange}
      ariaLabel={ariaLabel}
      placeholder={placeholder}
      isValidate={isDate}
      errorMessage="유효한 날짜 타입이 아닙니다."
    />
  );
};

export default DateInput;
