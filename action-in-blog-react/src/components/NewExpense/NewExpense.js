import { useState } from 'react';
import './NewExpense.css';

import ExpenseForm from './ExpenseForm';

const NewExpense = (props) => {

    const [flag, setFlag] = useState(false);

    const openFormHandler = () => {
        setFlag(true);
    };

    const closeFormHandler = () => {
        setFlag(false);
    }

    return (
        <div className="new-expense">
            {!flag && <button onClick={openFormHandler}>Add Expense</button>}
            {flag && <ExpenseForm onSaveExpenseData={props.onSaveExpenseData} onCloseFormHandler={closeFormHandler}/> }
        </div>
    );
}

export default NewExpense;