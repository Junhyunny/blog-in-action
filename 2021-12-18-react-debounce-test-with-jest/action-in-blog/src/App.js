import {useCallback, useState} from "react";
import axios from "axios";
import classes from './App.module.css';

function App() {

    const [apiCallCount, setApiCallCount] = useState(0);
    const [keyword, setKeyword] = useState('');

    const debounce = (func, timeout) => {
        let timer;
        return (...args) => {
            const context = this;
            if (timer) {
                clearTimeout(timer);
            }
            timer = setTimeout(() => {
                func.apply(context, args);
            }, timeout);
        };
    };

    const searchKeyword = (params) => {
        setApiCallCount(prevState => prevState + 1);
        axios.get('http://localhost:8080/search', {
            params
        });
    };

    const deboundHandler = useCallback(debounce(searchKeyword, 500), []);

    const keywordChangeHandler = ({target: {value}}) => {
        setKeyword(value);
        deboundHandler({keyword: value});
    };

    return (
        <div className={classes.App}>
            <input placeholder="검색어" value={keyword} onChange={keywordChangeHandler}/>
            <p>현재 API 호출 횟수 = {apiCallCount}</p>
        </div>
    );
}

export default App;
