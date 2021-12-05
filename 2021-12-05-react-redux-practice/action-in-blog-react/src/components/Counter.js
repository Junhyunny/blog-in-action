import { useSelector, useDispatch } from 'react-redux';
import { counterActions } from '../store/counter';

import classes from './Counter.module.css';

const Counter = () => {

  const dispatch = useDispatch();

  const { count, showCount } = useSelector((state) => {
    return state.counter;
  });

  const toggleCounterHandler = () => {
    dispatch(counterActions.toggleCounter())
  };

  const dispatchToIncrese = (amount) => {
    return () => dispatch(counterActions.increment({ amount: amount }));
  };

  const dispatchToDecrease = (amount) => {
    return () => dispatch(counterActions.decrement({ amount: amount }));
  };

  return (
    <main className={classes.counter}>
      <h1>Redux Counter</h1>
      {showCount && <div className={classes.value}>{count}</div>}
      <div>
        <button onClick={dispatchToIncrese(1)}>Increment</button>
        <button onClick={dispatchToIncrese(5)}>Increment by 5</button>
        <button onClick={dispatchToDecrease(1)}>Decrement</button>
      </div>
      <button onClick={toggleCounterHandler}>Toggle Counter</button>
    </main>
  );
};

export default Counter;
