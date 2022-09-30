import {useRef, useState} from "react";
import {addTodo} from "../repository/TodoRepository"

function Todos() {

    const todoRef = useRef()

    const [todos, setTodos] = useState([])
    const [valid, setValid] = useState(true)

    const addTodoHandler = () => {
        const newTodo = todoRef.current.value
        if (newTodo === '') {
            setValid(false)
            return
        }
        todoRef.current.value = ''
        setValid(true)
        setTodos(prevState => {
            return [...prevState, newTodo]
        })
        addTodo(newTodo)
    }

    return (
        <div>
            <h2>TODO LIST</h2>
            <input type="text" placeholder="NEW TODO" ref={todoRef}/>
            {!valid && <p>please write something</p>}
            <button onClick={addTodoHandler}>ADD</button>
            <ul>
                {todos.map((todo, index) => <li key={index}>{todo}</li>)}
            </ul>
        </div>
    )
}

export default Todos;