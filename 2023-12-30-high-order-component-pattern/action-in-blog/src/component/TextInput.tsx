type Props = {
  value: string;
  onValueChange: (value: string) => void;
  ariaLabel?: string;
  placeholder?: string;
  isError?: boolean;
  focusoutHandler?: () => void;
};

const TextInput = ({
  ariaLabel,
  value,
  onValueChange,
  placeholder,
  isError,
  focusoutHandler,
}: Props) => (
  <input
    className={isError ? "error" : ""}
    type="text"
    aria-label={ariaLabel}
    value={value}
    onChange={(e) => onValueChange(e.target.value)}
    onBlur={focusoutHandler}
    placeholder={placeholder}
  />
);

export default TextInput;
