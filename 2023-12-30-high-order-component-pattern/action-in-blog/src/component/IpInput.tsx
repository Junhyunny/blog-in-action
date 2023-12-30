import { isIpAddress } from "../util/validate";
import { ValidationTextInput } from "./ValidationComponents";

type Props = {
  value: string;
  onValueChange: (value: string) => void;
  ariaLabel?: string;
  placeholder?: string;
};

const IpInput = ({ ariaLabel, value, onValueChange, placeholder }: Props) => {
  return (
    <ValidationTextInput
      value={value}
      onValueChange={onValueChange}
      ariaLabel={ariaLabel}
      placeholder={placeholder}
      isValidate={isIpAddress}
      errorMessage="유효한 IP가 아닙니다."
    />
  );
};

export default IpInput;
