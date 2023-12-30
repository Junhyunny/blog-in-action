import { isPhoneContact } from "../util/validate";
import { ValidationTextInput } from "./ValidationComponents";

type Props = {
  value: string;
  onValueChange: (value: string) => void;
  ariaLabel?: string;
  placeholder?: string;
};

const PhoneContactInput = ({
  ariaLabel,
  value,
  onValueChange,
  placeholder,
}: Props) => {
  return (
    <ValidationTextInput
      value={value}
      onValueChange={onValueChange}
      ariaLabel={ariaLabel}
      placeholder={placeholder}
      isValidate={isPhoneContact}
      errorMessage="유효한 연락처 형식이 아닙니다."
    />
  );
};

export default PhoneContactInput;
