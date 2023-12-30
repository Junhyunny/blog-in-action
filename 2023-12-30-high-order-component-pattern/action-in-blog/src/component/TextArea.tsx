type Props = {
  value: string;
  onValueChange: (value: string) => void;
  ariaLabel?: string;
  placeholder?: string;
  isError?: boolean;
  focusoutHandler?: () => void;
};

const TextArea = ({
  ariaLabel,
  value,
  onValueChange,
  placeholder,
  isError,
  focusoutHandler,
}: Props) => (
  <textarea
    className={isError ? "error" : ""}
    aria-label={ariaLabel}
    value={value}
    onChange={(e) => onValueChange(e.target.value)}
    onBlur={focusoutHandler}
    placeholder={placeholder}
  />
);

export default TextArea;
