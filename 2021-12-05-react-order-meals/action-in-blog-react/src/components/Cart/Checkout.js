import classes from './Checkout.module.css';
import useCheckout from '../../hooks/useCheckout';

const Checkout = props => {

    const {
        value: nameValue,
        isValid: nameIsValid,
        changeValueHandler: changeNameHandler
    } = useCheckout(value => (value.trim() !== ''));

    const {
        value: streetValue,
        isValid: streetIsValid,
        changeValueHandler: changeStreetHandler
    } = useCheckout(value => (value.trim() !== ''));

    const {
        value: postalCodeValue,
        isValid: postalCodeIsValid,
        changeValueHandler: changePostalCodeHandler
    } = useCheckout(value => (value.trim().length === 5));

    const {
        value: cityValue,
        isValid: cityIsValid,
        changeValueHandler: changeCityHandler
    } = useCheckout(value => (value.trim() !== ''));

    const submitHandler = (event) => {
        event.preventDefault();
        const formIsValid = nameIsValid && streetIsValid && postalCodeIsValid && cityIsValid;
        if (!formIsValid) {
            return;
        }
        // submit the cart data
        props.onConfirm({
            name: nameValue,
            street: streetValue,
            postalCode: postalCodeValue,
            city: cityValue
        });
    };

    const nameControlClasses = `${classes.control} ${nameIsValid ? '' : classes.invalid}`
    const streetControlClasses = `${classes.control} ${streetIsValid ? '' : classes.invalid}`
    const cityControlClasses = `${classes.control} ${cityIsValid ? '' : classes.invalid}`
    const postalCodeControlClasses = `${classes.control} ${postalCodeIsValid ? '' : classes.invalid}`

    return (
        <form onSubmit={submitHandler}>
            <div className={nameControlClasses}>
                <label htmlFor='name'>Your Name</label>
                <input type='text' id='name' onChange={changeNameHandler} value={nameValue} />
                {!nameIsValid && <p>Please entere a valid name</p>}
            </div>
            <div className={streetControlClasses}>
                <label htmlFor='street'>Street</label>
                <input type='text' id='street' onChange={changeStreetHandler} value={streetValue} />
                {!streetIsValid && <p>Please entere a valid street</p>}
            </div>
            <div className={postalCodeControlClasses}>
                <label htmlFor='postal'>Postal Code</label>
                <input type='text' id='postal' onChange={changePostalCodeHandler} value={postalCodeValue} />
                {!postalCodeIsValid && <p>Please entere a valid postal code(length should be five.)</p>}
            </div>
            <div className={cityControlClasses}>
                <label htmlFor='city'>City</label>
                <input type='text' id='city' onChange={changeCityHandler} value={cityValue} />
                {!cityIsValid && <p>Please entere a valid city</p>}
            </div>
            <div className={classes.actions}>
                <button type="button" onClick={props.onCancel}>Cancel</button>
                <button type="submit" className={classes.submit}>Confirm</button>
            </div>
        </form>
    );
};

export default Checkout;