import { useState } from "react";

import ExpenseList from "./ExpenseList";
import ExpenseFilter from "./ExpenseFilter";
import './Expenses.css';
import ExpenseChart from "./ExpenseChart";

const Expenses = (props) => {

    const [year, setYear] = useState('2021');

    const yearChangeHandler = (selectedYear) => {
        setYear(selectedYear);
    };

    const filteredItems = props.items.filter((item) => {
        return item.date.getFullYear().toString() === year;
    });

    return (
        <div className="expenses">
            <ExpenseFilter onYearChangeHandler={yearChangeHandler} />
            <ExpenseChart items={filteredItems}/>
            <ExpenseList items={filteredItems}/>
        </div>
    );
};

export default Expenses;