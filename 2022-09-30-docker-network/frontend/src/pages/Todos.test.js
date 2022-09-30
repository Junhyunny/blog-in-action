import {render, screen} from "@testing-library/react";
import userEvent from "@testing-library/user-event";

import Todos from "./Todos";

import * as TodoRepository from "../repository/TodoRepository";

describe('Todos Page Test', () => {

    const addTodo = jest.spyOn(TodoRepository, 'addTodo')

    test('render Todos static elements', () => {
        render(<Todos/>)

        expect(screen.getByText('TODO LIST')).toBeInTheDocument()
        expect(screen.getByPlaceholderText('NEW TODO')).toBeInTheDocument()
        expect(screen.getByRole('button', {name: 'ADD'})).toBeInTheDocument()
    })

    test('render new todo item when click the ADD button and clear text box', async () => {
        render(<Todos/>)
        await userEvent.type(screen.getByPlaceholderText('NEW TODO'), 'Hello World')
        await userEvent.click(screen.getByRole('button', {name: 'ADD'}))

        expect(screen.getByText('Hello World')).toBeInTheDocument()
        expect(screen.getByPlaceholderText('NEW TODO').value).toBe('')
    })

    test('render "please write something" error message when click ADD button with empty text box', async () => {
        render(<Todos/>)
        await userEvent.click(screen.getByRole('button', {name: 'ADD'}))

        expect(screen.getByText('please write something')).toBeInTheDocument()
    })

    test('do not render error message when add new todo after error', async () => {
        render(<Todos/>)
        await userEvent.click(screen.getByRole('button', {name: 'ADD'}))
        await userEvent.type(screen.getByPlaceholderText('NEW TODO'), 'Hello World')
        await userEvent.click(screen.getByRole('button', {name: 'ADD'}))

        expect(screen.queryByText('please write something')).not.toBeInTheDocument()
        expect(screen.getByText('Hello World')).toBeInTheDocument()
    })

    test('call addTodo method when click ADD button', async () => {
        render(<Todos/>)
        await userEvent.type(screen.getByPlaceholderText('NEW TODO'), 'Hello World')
        await userEvent.click(screen.getByRole('button', {name: 'ADD'}))

        expect(addTodo).nthCalledWith(1, 'Hello World')
    })
})