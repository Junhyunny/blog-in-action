import {render, screen, waitFor} from "@testing-library/react";
import userEvent from "@testing-library/user-event";

import Todos from "./Todos";

import * as TodoRepository from "../repository/TodoRepository";

describe('Todos Page Test', () => {

    const renderComponent = async (component, todos = []) => {
        const addTodo = jest.spyOn(TodoRepository, 'addTodo').mockResolvedValue(null)
        const getTodos = jest.spyOn(TodoRepository, 'getTodos').mockResolvedValue(todos)

        await waitFor(() => {
            render(component)
        })

        return {
            addTodo,
            getTodos
        }
    }

    test('render Todos static elements', async () => {
        await renderComponent(<Todos/>)

        expect(screen.getByText('TODO LIST')).toBeInTheDocument()
        expect(screen.getByPlaceholderText('NEW TODO')).toBeInTheDocument()
        expect(screen.getByRole('button', {name: 'ADD'})).toBeInTheDocument()
    })

    test('render new todo item when click the ADD button and clear text box', async () => {
        await renderComponent(<Todos/>, [
            {id: '1', title: 'Hello World'}
        ])
        await userEvent.type(screen.getByPlaceholderText('NEW TODO'), 'Hello World')
        await userEvent.click(screen.getByRole('button', {name: 'ADD'}))

        expect(screen.getByText('Hello World')).toBeInTheDocument()
        expect(screen.getByPlaceholderText('NEW TODO').value).toBe('')
    })

    test('render "please write something" error message when click ADD button with empty text box', async () => {
        await renderComponent(<Todos/>)
        await userEvent.click(screen.getByRole('button', {name: 'ADD'}))

        expect(screen.getByText('please write something')).toBeInTheDocument()
    })

    test('do not render error message when add new todo after error', async () => {
        await renderComponent(<Todos/>, [
            {id: '1', title: 'Hello World'}
        ])
        await userEvent.click(screen.getByRole('button', {name: 'ADD'}))
        await userEvent.type(screen.getByPlaceholderText('NEW TODO'), 'Hello World')
        await userEvent.click(screen.getByRole('button', {name: 'ADD'}))

        expect(screen.queryByText('please write something')).not.toBeInTheDocument()
        expect(await screen.findByText('Hello World')).toBeInTheDocument()
    })

    test('render added todos', async () => {
        await renderComponent(<Todos/>, [
            {id: '1', title: 'Hello World'},
            {id: '2', title: 'I am Junhyunny'},
        ])

        expect(await screen.findByText('Hello World')).toBeInTheDocument()
        expect(screen.getByText('I am Junhyunny')).toBeInTheDocument()
    })
})