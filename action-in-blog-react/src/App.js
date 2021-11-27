import Expenses from "./components/Expenses/Expenses";
import NewExpense from "./components/NewExpense/NewExpense";

const App = () => {

    const expenses = [
        { id: 'e1', title: 'Toilet Paper', amount: 67.67, date: new Date(2021, 2, 28) },
        { id: 'e2', title: 'Car Insurance', amount: 294.67, date: new Date(2021, 2, 28) },
    ];
    
    const onSaveExpenseData = (expenseData) => {
        const newExpenseData = {
            ...expenseData,
            id: Math.random().toString()
        }
        expenses.push(newExpenseData);
        console.log(expenses);
    };

    return (
        <div>
            <h2>Let's get started!</h2>
            <NewExpense onSaveExpenseData={onSaveExpenseData}/>
            <Expenses items={expenses} />
        </div>
    );
}

export default App;
