import { useState } from 'react'; 

import Expenses from "./components/Expenses/Expenses";
import NewExpense from "./components/NewExpense/NewExpense";

const App = () => {

    const [expenses, setExpenses] = useState([
        { id: 'e1', title: 'Toilet Paper', amount: 67.67, date: new Date(2021, 2, 28), display: true },
        { id: 'e2', title: 'Car Insurance', amount: 294.67, date: new Date(2021, 2, 28), display: true },
    ]);
    
    const [year, setYear] = useState('2021');
    
    const onSaveExpenseData = (expenseData) => {
        const newExpenseData = {
            ...expenseData,
            id: Math.random().toString(),
            display: expenseData.date.getFullYear().toString() === year
        }
        setExpenses((preExpense) => {
            return [newExpenseData, ...preExpense];
        });
    };

    const yearChangeHandler = (selectedYear) => {
        setYear(selectedYear);
        setExpenses((preExpense) => {
            return [...preExpense.map((expense) => {
                expense.display = expense.date.getFullYear().toString() === selectedYear;
                return expense;
            })];
        });
    };

    return (
        <div>
            <NewExpense onSaveExpenseData={onSaveExpenseData}/>
            <Expenses items={expenses}  onYearChangeHandler= {yearChangeHandler} />
        </div>
    );
}

export default App;
