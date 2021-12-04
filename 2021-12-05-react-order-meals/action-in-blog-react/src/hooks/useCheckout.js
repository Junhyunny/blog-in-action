import { useState } from "react";

const useCheckout = (validateCheck) => {

    const [value, setValue] = useState('');
    const isValid = validateCheck(value);

    const changeValueHandler = (event) => {
        setValue(event.target.value);
    };
    
    return {
        value,
        isValid,
        changeValueHandler
    };
};

export default useCheckout;