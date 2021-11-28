import { useState } from 'react'; 

import Expenses from "./components/Expenses/Expenses";
import NewExpense from "./components/NewExpense/NewExpense";

const App = () => {

    const [expenses, setExpenses] = useState([
        { id: 'e1', title: 'Toilet Paper', amount: 67.67, date: new Date(2021, 2, 28) },
        { id: 'e2', title: 'Car Insurance', amount: 294.67, date: new Date(2021, 2, 28) },
    ]);
    
    const onSaveExpenseData = (expenseData) => {
        const newExpenseData = {
            ...expenseData,
            id: Math.random().toString(),
        }
        setExpenses((preExpense) => {
            return [newExpenseData, ...preExpense];
        });
    };

    return (
        <div>
            <NewExpense onSaveExpenseData={onSaveExpenseData}/>
            <Expenses items={expenses}/>
        </div>
    );
}

export default App;
