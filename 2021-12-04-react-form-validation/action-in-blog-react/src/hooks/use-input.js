import { useReducer } from "react";

const initialInputState = {
    value: '',
    isTouched: false
};

const inputStateReducer = (state, action) => {
    if (action.type === 'INPUT') {
        return { value: action.value, isTouched: state.isTouched };
    } else if (action.type === 'BLUR') {
        return { value: state.value, isTouched: true };
    } else if (action.type === 'RESET') {
        return { value: '', isTouched: false };
    }
    return initialInputState;
};

const useInput = (validateValue) => {

    const [inputState, dispatchInput] = useReducer(inputStateReducer, initialInputState);

    const valueIsValid = validateValue(inputState.value);
    const hasError = !valueIsValid && inputState.isTouched;

    const inputBlurHandler = () => {
        dispatchInput({
            type: 'BLUR',
            isTouched: true
        });
    };

    const valueChangeHandler = event => {
        dispatchInput({
            type: 'INPUT',
            value: event.target.value
        });
    };

    const reset = () => {
        dispatchInput({
            type: 'RESET'
        });
    };

    return {
        value: inputState.value,
        hasError,
        valueIsValid,
        reset,
        inputBlurHandler,
        valueChangeHandler
    };
};

export default useInput;