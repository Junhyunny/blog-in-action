import "./App.css";
import { useEffect, useRef, useState } from "react";
import axios from "axios";

type Todo = {
  id: number;
  title: string;
};

function App() {
  const inputRef = useRef<HTMLInputElement>(null);
  const [todos, setTodos] = useState<Todo[]>([]);

  useEffect(() => {
    fetchTodos();
  }, []);

  const fetchTodos = () => {
    axios.get("/api/todos").then((response) => setTodos(response.data));
  };

  const createTodo = () => {
    const input = inputRef.current;
    if (input) {
      axios.post("/api/todos", { title: input.value }).then(fetchTodos);
      input.value = "";
    }
  };

  return (
    <>
      <h1>TODO LIST</h1>
      <input type="text" placeholder="add new todo..." ref={inputRef} />
      <button onClick={createTodo}>submit</button>
      <ul>
        {todos.map((todo) => (
          <li key={todo.id}>{todo.title}</li>
        ))}
      </ul>
    </>
  );
}

export default App;
