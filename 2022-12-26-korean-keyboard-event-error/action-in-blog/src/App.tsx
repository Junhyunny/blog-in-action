import React, { ChangeEvent, KeyboardEvent, useState } from "react";
import "./App.css";

function App() {
  const [todo, setTodo] = useState<string>("");
  const [todoList, addTodoList] = useState<string[]>([]);

  const onChangeHandler = (event: ChangeEvent<HTMLInputElement>) => {
    setTodo(event.target.value);
  };

  const onKeyboardEvent = (event: KeyboardEvent<HTMLInputElement>) => {
    if (event.nativeEvent.isComposing) {
      return;
    }
    if (event.key === "Enter") {
      addTodoList((prevState) => {
        return [...prevState, todo];
      });
      setTodo("");
    }
  };

  return (
    <div className="App">
      <input
        type="text"
        value={todo}
        onChange={onChangeHandler}
        onKeyDown={onKeyboardEvent}
      />
      <div>
        {todoList.map((todo, index) => (
          <div key={index} className="todo">
            {todo}
          </div>
        ))}
      </div>
    </div>
  );
}

export default App;
