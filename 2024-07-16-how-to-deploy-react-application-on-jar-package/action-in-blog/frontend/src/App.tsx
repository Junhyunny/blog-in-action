import { KeyboardEvent, useCallback, useEffect, useRef, useState } from "react";
import styles from "./App.module.css";

type Todo = {
  title: string;
};

function App() {
  const inputRef = useRef<HTMLInputElement>(null);
  const [todos, setTodos] = useState<Todo[]>([]);

  const fetchAll = useCallback(
    () =>
      fetch(`${process.env.VITE_BACKEND_URL}/api/todos`, {
        method: "get",
      }).then((response) => response.json()),
    [],
  );

  const createTodo = useCallback(
    (value: string) =>
      fetch(`${process.env.VITE_BACKEND_URL}/api/todos`, {
        method: "post",
        body: JSON.stringify({ title: value }),
        headers: {
          "Content-Type": "application/json",
        },
      }),
    [],
  );

  const keyDownHandler = (event: KeyboardEvent<HTMLInputElement>) => {
    if (
      inputRef.current &&
      inputRef.current.value !== "" &&
      event.key === "Enter"
    ) {
      createTodo(inputRef.current.value)
        .then(fetchAll)
        .then((data) => setTodos(data));
      inputRef.current.value = "";
    }
  };

  useEffect(() => {
    fetchAll().then((data) => setTodos(data));
  }, [fetchAll]);

  return (
    <div className={styles.container}>
      <h3>Todo List</h3>
      <div>
        <label htmlFor="todo">
          <input
            type="text"
            id="todo"
            name="todo"
            onKeyDown={keyDownHandler}
            ref={inputRef}
          />
        </label>
      </div>
      <ul>
        {todos.map((todo, index) => (
          <li key={index}>{todo.title}</li>
        ))}
      </ul>
    </div>
  );
}

export default App;
