import {useEffect, useRef, useState} from "react";
import {addTodo, getTodos} from "../repository/TodoRepository"

function Todos() {

    const todoRef = useRef()

    const [todos, setTodos] = useState([])
    const [valid, setValid] = useState(true)

    const fetchTodos = () => {
        getTodos().then(todos => {
            setTodos(todos)
        })
    }

    const addTodoHandler = () => {
        const title = todoRef.current.value
        if (title === '') {
            setValid(false)
            return
        }
        todoRef.current.value = ''
        setValid(true)
        addTodo(title).then(fetchTodos)
    }

    useEffect(() => {
        fetchTodos()
    }, [])

    return (
        <div>
            <h2>TODO LIST</h2>
            <input type="text" placeholder="NEW TODO" ref={todoRef}/>
            {!valid && <p>please write something</p>}
            <button onClick={addTodoHandler}>ADD</button>
            <ul>
                {todos.map((todo) => <li key={todo.id}>{todo.title}</li>)}
            </ul>
        </div>
    )
}

export default Todos;