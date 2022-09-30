import axios from "axios";
import {addTodo, getTodos} from "./TodoRepository";

describe('TodoRepository Test', () => {

    test('call post method with parameter', () => {
        const post = jest.spyOn(axios, 'post')

        addTodo('Hello World')

        expect(post).nthCalledWith(1, '/test/todo', {title: 'Hello World'}, {
            headers: {
                'Content-Type': 'application/json'
            }
        })
    })

    test('call get method and return todos', async () => {
        const get = jest.spyOn(axios, 'get').mockResolvedValue({
            data: [{id: '1', title: 'Hello World'}]
        })

        const todos = await getTodos()

        expect(get).nthCalledWith(1, '/test/todos')
        expect(todos[0].id).toEqual('1')
        expect(todos[0].title).toEqual('Hello World')
    })
})