import { useEffect, useState } from "react";

function CustomButton(props) {
  const clickHandler = () => {
    console.log(`this click handler is added when count is ${props.count}`);
  };

  useEffect(() => {
    window.addEventListener("click", clickHandler);
    return () => {
      window.removeEventListener("click", clickHandler);
    };
  }, []);

  return <button onClick={props.onClick}>Custom Click me</button>;
}

function App() {
  const [count, setCount] = useState(0);

  const increaseCount = () => {
    setCount(count + 1);
  };

  return (
    <div>
      <p>You clicked {count} times</p>
      {count % 2 === 0 && <button onClick={increaseCount}>Click me</button>}
      {count % 2 === 1 && (
        <CustomButton onClick={increaseCount} count={count} />
      )}
    </div>
  );
}

export default App;
