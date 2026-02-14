import "./App.css";
import type { AxiosResponse } from "axios";
import { useState } from "react";
import {
  getGetTodosQueryKey,
  useGetTodos,
  usePostTodos,
} from "./apis/todoAPI.ts";
import type { Todo } from "./model";

const TodoItem = ({ todo }: { todo: Todo }) => {
  return (
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
  );
};

function App() {
  const getTodoQueryKey = getGetTodosQueryKey();
  const { data: todos = { data: [] } } = useGetTodos();
  const { mutate: createTodo } = usePostTodos({
    mutation: {
      onMutate: async (newTodo, context) => {
        // [1]
        await context.client.cancelQueries({ queryKey: getTodoQueryKey });
        // [2]
        const previousState = context.client.getQueryData(getTodoQueryKey);
        // [3]
        context.client.setQueryData(
          getTodoQueryKey,
          (prev: AxiosResponse<Todo[]>) => ({
            ...prev,
            data: [{ ...newTodo.data, id: Math.random() * -1 }, ...prev.data],
          }),
        );
        // [4]
        return { previousState };
      },
      onError: (_err, _newTodo, onMutateResult, context) => {
        if (onMutateResult?.previousState) {
          context.client.setQueryData(
            getTodoQueryKey,
            onMutateResult.previousState,
          );
        }
      },
      onSettled: async (
        _data,
        _error,
        _variables,
        _onMutateResult,
        context,
      ) => {
        await context.client.invalidateQueries({ queryKey: getTodoQueryKey });
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
            <TodoItem todo={todo} />
          </div>
        ))}
      </div>
    </div>
  );
}

export default App;
