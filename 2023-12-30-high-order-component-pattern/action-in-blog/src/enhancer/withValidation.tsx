import { JSX, useCallback, useState } from "react";

export type Props = {
  value: any;
  onValueChange: (value: any) => void;
  isValidate: (value: any) => boolean;
  errorMessage: string;
  ariaLabel?: string;
  placeholder?: string;
};

export const withValidation =
  (WrappedComponent: (props: any) => JSX.Element) => (props: Props) => {
    const [isError, setError] = useState<boolean>(false);

    const focusoutHandler = useCallback(() => {
      setError(!props.isValidate(props.value));
    }, [props]);

    const newProps = {
      ...props,
      isError,
      focusoutHandler,
    };

    return (
      <div className="validate-component">
        <WrappedComponent {...newProps} />
        {isError && <div className="error">{props.errorMessage}</div>}
      </div>
    );
  };
