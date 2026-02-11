import "./App.css";
import { useQueryClient } from "@tanstack/react-query";
import { useState } from "react";
import {
  getGetTodosQueryKey,
  useGetTodos,
  usePostTodos,
} from "./apis/todoAPI.ts";

function App() {
  const queryClient = useQueryClient();
  const { data: todos = { data: [] } } = useGetTodos();
  const { mutate: createTodo } = usePostTodos({
    mutation: {
      onSuccess: async () => {
        await queryClient.invalidateQueries({
          queryKey: getGetTodosQueryKey(),
        });
      },
    },
  });

  const [item, setItem] = useState("");
  const handleSubmit = async () => {
    createTodo({ data: { title: item, completed: false } });
    setItem("");
  };

  return (
    <div style={{ display: "flex", flexDirection: "column", gap: "1rem" }}>
      <div style={{ display: "flex", gap: "1rem" }}>
        <input
          type="text"
          placeholder="해야할 일"
          onChange={(e) => setItem(e.target.value)}
          value={item}
        />
        <button type="button" onClick={handleSubmit}>
          등록
        </button>
      </div>
      <div>
        {todos.data.map((todo) => (
          <div key={todo.id}>
            <div
              style={{
                display: "flex",
                gap: "0.5rem",
                justifyContent: "flex-start",
              }}
            >
              <input type="checkbox" checked={todo.completed} readOnly />
              <span>{todo.title}</span>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default App;
