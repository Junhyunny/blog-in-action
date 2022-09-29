import {render, screen} from "@testing-library/react";
import Todos from "./Todos";
import userEvent from "@testing-library/user-event";

describe('Todos Page Test', () => {

    test('render TODO LIST title', () => {
        render(<Todos/>)

        expect(screen.getByText('TODO LIST')).toBeInTheDocument()
    })

    test('render text box and ADD button', () => {
        render(<Todos/>)

        expect(screen.getByPlaceholderText('NEW TODO')).toBeInTheDocument()
        expect(screen.getByRole('button', {name: 'ADD'})).toBeInTheDocument()
    })

    test('render new todo item when click the ADD button', () => {
        render(<Todos/>)
        userEvent.type(screen.getByPlaceholderText('NEW TODO'), 'Hello World')
        userEvent.click(screen.getByRole('button', {name: 'ADD'}))

        expect(screen.getByText('Hello World')).toBeInTheDocument()
    })

    test('render error "please write something" message when click ADD button with empty text box', () => {
        render(<Todos/>)
        userEvent.click(screen.getByRole('button', {name: 'ADD'}))

        expect(screen.getByText('please write something')).toBeInTheDocument()
    })

    test('clear text box when succeed add new todo', () => {
        render(<Todos/>)
        userEvent.type(screen.getByPlaceholderText('NEW TODO'), 'Hello World')
        userEvent.click(screen.getByRole('button', {name: 'ADD'}))

        expect(screen.getByPlaceholderText('NEW TODO').value).toBe('')
    })
})