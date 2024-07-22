import { useRef } from "react";

function App() {
  const inputRef = useRef<HTMLInputElement>(null);

  const reset = () => {
    if (inputRef.current) {
      inputRef.current.value = "";
    }
  };

  return (
    <>
      <input type="text" ref={inputRef} placeholder="username" />
      <button onClick={reset}>reset</button>
    </>
  );
}

export default App;
