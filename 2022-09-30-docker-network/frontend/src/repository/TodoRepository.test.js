import axios from "axios";
import {addTodo} from "./TodoRepository";

describe('TodoRepository Test', () => {

    test('call post method with parameter', () => {
        const post = jest.spyOn(axios, 'post')

        addTodo('Hello World')

        expect(post).nthCalledWith(1, '/todo', {title: 'Hello World'}, {
            headers: {
                'Content-Type': 'application/json'
            }
        })
    })
})