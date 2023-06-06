import React, { useEffect, useRef, useState } from "react";
import "./App.css";
import axios from "axios";
import { Todo } from "./types/Todo";

function App() {
  const todoTextInputRef = useRef<HTMLInputElement>(null);
  const [todoList, setTodoList] = useState<Todo[]>([]);

  useEffect(() => {
    axios.get("/todos").then((response) => {
      const { data: todoList } = response;
      setTodoList(todoList);
    });
  }, []);

  const addHandler = async () => {
    const { data: newTodo } = await axios.post("/todos", {
      content: todoTextInputRef.current?.value,
    });
    setTodoList((todoList) => [...todoList, newTodo]);
    todoTextInputRef.current!.value = "";
  };

  return (
    <div className="app">
      <div className="todo-list">
        <div>
          {todoList.map((todo) => (
            <li key={todo.id}>{todo.content}</li>
          ))}
        </div>
      </div>
      <div className="todo-form">
        <input ref={todoTextInputRef} type="text" placeholder="NEW TODO" />
        <button onClick={addHandler}>ADD</button>
      </div>
    </div>
  );
}

export default App;
