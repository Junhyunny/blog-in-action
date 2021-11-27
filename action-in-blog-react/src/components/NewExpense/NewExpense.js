import './NewExpense.css';

import ExpenseForm from './ExpenseForm';

const NewExpense = (props) => {
    return (
        <div className="new-expense">
            <ExpenseForm onSaveExpenseData={props.onSaveExpenseData} />
        </div>
    );
}

export default NewExpense;