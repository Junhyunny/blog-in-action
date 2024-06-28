import { useCallback, useState } from 'react';
import { getItems } from './respository/ItemRepository';
import { debounce } from './util/debounce';
import classes from './App.module.css';

function App() {
  const [keyword, setKeyword] = useState('');
  const [apiCallCount, setApiCallCount] = useState(0);

  const searchKeyword = (keyword) => {
    setApiCallCount((prevState) => prevState + 1);
    getItems({ keyword }).then(console.log);
  };

  const debounceSearch = useCallback(debounce(searchKeyword, 500), []);

  const keywordChangeHandler = ({ target: { value } }) => {
    setKeyword(value);
    debounceSearch(value);
  };

  return (
    <div className={classes.App}>
      <input placeholder="검색어" value={keyword} onChange={keywordChangeHandler} />
      <p>현재 API 호출 횟수 = {apiCallCount}</p>
    </div>
  );
}

export default App;
