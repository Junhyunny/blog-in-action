import ExpenseItem from "./ExpenseItem";
import ExpenseFilter from "./ExpenseFilter";
import './Expenses.css';

const Expenses = (props) => {
    return (
        <div className="expenses">
            <ExpenseFilter onYearChangeHandler={props.onYearChangeHandler}/>
            {
                props.items
                    .filter((item) => item.display)
                    .map((item) => (
                        <ExpenseItem 
                            key={item.id}
                            title={item.title} 
                            amount={item.amount} 
                            date={item.date} />
                ))
            }
        </div>
    );
};

export default Expenses;