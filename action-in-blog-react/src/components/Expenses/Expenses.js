import ExpenseItem from "./ExpenseItem";
import './Expenses.css';

const Expenses = (props) => {
    return (
        <div className="expenses">
            <ExpenseItem title={props.items[0].title} amount={props.items[0].amount} date={props.items[0].date} />
            <ExpenseItem title={props.items[1].title} amount={props.items[1].amount} date={props.items[1].date} />
        </div>
    );
};

export default Expenses;