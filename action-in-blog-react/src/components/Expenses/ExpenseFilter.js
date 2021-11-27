import './ExpenseFilter.css';

const ExpenseFilter = (props) => {

    const yearChangeHandler = (event) => {
        props.onYearChangeHandler(event.target.value);
    };

    return (
        <div className="expense-filter">
            <label>
                Filter by year
            </label>
            <select onChange={yearChangeHandler}>
                <option value="2021">2021</option>
                <option value="2020">2020</option>
                <option value="2019">2019</option>
                <option value="2018">2018</option>
                <option value="2017">2017</option>
            </select>
        </div>        
    );
};

export default ExpenseFilter;