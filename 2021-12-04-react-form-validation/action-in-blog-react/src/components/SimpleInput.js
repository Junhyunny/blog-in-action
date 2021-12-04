import useInput from "../hooks/use-input";

const SimpleInput = (props) => {

  const {
    value: enteredName, 
    hasError: nameInputIsInvalid,
    valueIsValid: enteredNameIsValid,
    reset: resetName,
    valueChangeHandler: nameChangeHandler,
    inputBlurHandler: nameBlurHandler
  } = useInput((value) => (value.trim() !== ''));

  const {
    value: enteredEmail, 
    hasError: emailInputIsInvalid,
    valueIsValid: enteredEmailIsValid,
    reset: resetEmail,
    valueChangeHandler: emailChangeHandler,
    inputBlurHandler: emailBlurHandler
  } = useInput((value) => (value.includes('@')));

  let formIsValid = false;
  if (enteredNameIsValid && enteredEmailIsValid) {
    formIsValid = true;
  }

  const formSubmissionHandler = event => {
    event.preventDefault();
    if (!formIsValid) {
      return;
    }
    resetName();
    resetEmail();
    console.log('form is valid');
  };

  const nameInputClasses = nameInputIsInvalid ? 'form-control invalid' : 'form-control';

  const emailInputClasses = emailInputIsInvalid ? 'form-control invalid' : 'form-control';

  return (
    <form onSubmit={formSubmissionHandler}>
      <div className={nameInputClasses}>
        <label htmlFor='name'>Your Name</label>
        <input
          type='text'
          id='name'
          onChange={nameChangeHandler}
          onBlur={nameBlurHandler}
          value={enteredName} />
      </div>
      {nameInputIsInvalid &&
        <p className="error-text">
          Name must not be empty.
        </p>}
      <div className={emailInputClasses}>
        <label htmlFor='name'>Your Email</label>
        <input
          type='text'
          id='email'
          onChange={emailChangeHandler}
          onBlur={emailBlurHandler}
          value={enteredEmail} />
      </div>
      {emailInputIsInvalid &&
        <p className="error-text">
          Email must not be empty.
        </p>}
      <div className="form-actions">
        <button disabled={!formIsValid}>Submit</button>
      </div>
    </form>
  );
};

export default SimpleInput;
