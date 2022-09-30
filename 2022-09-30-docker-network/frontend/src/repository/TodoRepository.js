import axios from "axios";

export const addTodo = async (todo) => {
    await axios.post(`${process.env.REACT_APP_SERVER}/todo`, {title: todo}, {
        headers: {
            'Content-Type': 'application/json'
        }
    })
}
