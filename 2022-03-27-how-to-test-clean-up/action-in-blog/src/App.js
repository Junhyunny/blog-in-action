import { useState } from "react";
import CustomButton from "./components/CustomButton";

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
