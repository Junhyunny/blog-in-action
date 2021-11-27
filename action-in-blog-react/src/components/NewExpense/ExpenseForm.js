import { useState } from 'react';

import './ExpenseForm.css';

const ExpenseForm = (props) => {

    const [title, setTitle] = useState('');
    const [amount, setAmount] = useState('');
    const [date, setDate] = useState('');

    // const [userInput, setUserInput] =  useState({
    //     enteredTitle: '',
    //     enteredAmount: '',
    //     enteredDate: ''
    // });

    const titleChangeHandler = (event) => {
        setTitle(event.target.value);
        
        // dangerous way - not update state instancely, not guarantee prevState
        // setUserInput({
        //     ...userInput,
        //     enteredTitle: event.target.value
        // });
        
        // safe way - guarantee prevState
        // setUserInput((prevState) => {
        //     return {
        //         ...prevState,
        //         enteredTitle: event.target.value
        //     };
        // });
    };

    const amountChangeHandler = (event) => {
        setAmount(event.target.value);

        // dangerous way
        // setUserInput({
        //     ...userInput,
        //     enteredAmount: event.target.value
        // });
        
        // safe way
        // setUserInput((prevState) => {
        //     return {
        //         ...prevState,
        //         enteredTitle: event.target.value
        //     };
        // });
    };

    const dateChangeHandler = (event) => {
        setDate(event.target.value);
        
        // dangerous way
        // setUserInput({
        //     ...userInput,
        //     enteredDate: event.target.value
        // });
        
        // safe way
        // setUserInput((prevState) => {
        //     return {
        //         ...prevState,
        //         enteredTitle: event.target.value
        //     };
        // });
    };

    const submitHandler = (event) => {
        event.preventDefault();
        const expenseData = {
            title: title,
            amount: amount,
            date: new Date(date)
        };
        props.onSaveExpenseData(expenseData);
        setTitle('');
        setAmount('');
        setDate('');
    };

    return (
        <form onSubmit={submitHandler}>
            <div className="new-expense__controls">
                <div className="new-expense__control">
                    <label>Title</label>
                    <input type="text" value={title} onChange={titleChangeHandler} />
                </div>
                <div className="new-expense__control">
                    <label>Amount</label>
                    <input type="number" min="0.01" step="0.01" value={amount} onChange={amountChangeHandler}/>
                </div>
                <div className="new-expense__control">
                    <label>Date</label>
                    <input type="date" min="2019-01-01" step="2022-12-31" value={date} onChange={dateChangeHandler}/>
                </div>
            </div>
            <div className="new-expense_actions">
                <button onClick={props.onCloseFormHandler}>cancel</button>
                <button type="submit">Add Expense</button>
            </div>
        </form>
    );
}

export default ExpenseForm;