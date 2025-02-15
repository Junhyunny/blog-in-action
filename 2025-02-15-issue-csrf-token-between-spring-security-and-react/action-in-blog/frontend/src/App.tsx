import { useCallback, useEffect, useRef } from "react";
import "./App.css";
import axios from "axios";

function App() {
  const inputRef = useRef<HTMLInputElement>(null);

  const retrieveCsrfToken = useCallback(() => {
    axios.get("/api/csrf").then(({ data }) => {
      axios.defaults.headers[data.headerName] = data.token; // 2
    });
  }, []);

  const createTodo = useCallback(() => {
    const inputElement = inputRef.current;
    if (inputElement) {
      axios
        .post("/api/todos", {
          content: inputElement.value,
        })
        .then(() => alert("submitted"));
      inputElement.value = "";
    }
  }, []);

  useEffect(() => {
    retrieveCsrfToken(); // 1
  }, [retrieveCsrfToken]);

  return (
    <div>
      <input type="text" ref={inputRef} />
      <button onClick={createTodo}>submit</button>
    </div>
  );
}

export default App;
