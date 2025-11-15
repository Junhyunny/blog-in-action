import { useState, useEffect } from 'react';

const loadTodosFromStorage = (): string[] => {
  try {
    const savedTodos = localStorage.getItem('todos');
    if (savedTodos && savedTodos !== 'null') {
      const parsed = JSON.parse(savedTodos);
      return Array.isArray(parsed) ? parsed : [];
    }
  } catch (error) {
    console.warn('로컬 스토리지 데이터 파싱 오류:', error);
  }
  return [];
};

const saveTodosToStorage = (todosToSave: string[]) => {
  try {
    localStorage.setItem('todos', JSON.stringify(todosToSave));
  } catch (error) {
    console.warn('로컬 스토리지 저장 오류:', error);
  }
};

const TodoList = () => {
  const [inputValue, setInputValue] = useState('');
  const [todos, setTodos] = useState<string[]>(() => loadTodosFromStorage());

  useEffect(() => {
    saveTodosToStorage(todos);
  }, [todos]);

  const handleAddTodo = () => {
    const trimmedValue = inputValue.trim();
    if (trimmedValue) {
      setTodos([trimmedValue, ...todos]);
    }
    setInputValue('');
  };

  return (
    <div>
      <h1>TODO 리스트</h1>
      <input
        type="text"
        value={inputValue}
        onChange={(e) => setInputValue(e.target.value)}
      />
      <button onClick={handleAddTodo}>추가</button>

      {todos.length === 0 ? (
        <div>할 일이 없습니다.</div>
      ) : (
        <ul>
          {todos.map((todo, index) => (
            <li key={index} data-testid="todo-item">
              {todo}
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default TodoList;
