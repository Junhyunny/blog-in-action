import { isEmail } from "../util/validate";
import { ValidationTextInput } from "./ValidationComponents";

type Props = {
  value: string;
  onValueChange: (value: string) => void;
  ariaLabel?: string;
  placeholder?: string;
};

const EmailInput = ({
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
      isValidate={isEmail}
      errorMessage="유효한 이메일이 아닙니다."
    />
  );
};

export default EmailInput;
