import axios from 'axios'
import { render, screen, waitFor } from '@testing-library/react'
import App from './App'
import userEvent from '@testing-library/user-event'

describe('App', () => {
    it('renders filtered items', async () => {
        jest.spyOn(axios, 'get').mockResolvedValue({
            data: [
                { id: 1, name: 'hello' },
                { id: 2, name: 'hello world' },
                { id: 3, name: "junhyunny's devlog" },
            ],
        })
        render(<App />)

        await waitFor(() => {
            userEvent.type(screen.getByPlaceholderText('search'), 'hello')
        })

        await waitFor(() => {
            expect(screen.getByText('hello')).toBeInTheDocument()
        })
        expect(screen.getByText('hello world')).toBeInTheDocument()
        expect(screen.queryByText("junhyunny's devlog")).not.toBeInTheDocument()
    })

    it('renders filtered items - expect two times', async () => {
        jest.spyOn(axios, 'get').mockResolvedValue({
            data: [
                { id: 1, name: 'hello' },
                { id: 2, name: 'hello world' },
                { id: 3, name: "junhyunny's devlog" },
            ],
        })
        render(<App />)

        await waitFor(() => {
            expect(screen.getByText('hello')).toBeInTheDocument()
        })
        expect(screen.getByText('hello world')).toBeInTheDocument()
        expect(screen.getByText("junhyunny's devlog")).toBeInTheDocument()

        userEvent.type(screen.getByPlaceholderText('search'), 'hello')

        await waitFor(() => {
            expect(screen.getByText('hello')).toBeInTheDocument()
        })
        expect(screen.getByText('hello world')).toBeInTheDocument()
        expect(screen.queryByText("junhyunny's devlog")).not.toBeInTheDocument()
    })

    it('renders filtered items - using findByPlaceholderText function', async () => {
        jest.spyOn(axios, 'get').mockResolvedValue({
            data: [
                { id: 1, name: 'hello' },
                { id: 2, name: 'hello world' },
                { id: 3, name: "junhyunny's devlog" },
            ],
        })
        render(<App />)

        userEvent.type(await screen.findByPlaceholderText('search'), 'hello')

        await waitFor(() => {
            expect(screen.getByText('hello')).toBeInTheDocument()
        })
        expect(screen.getByText('hello world')).toBeInTheDocument()
        expect(screen.queryByText("junhyunny's devlog")).not.toBeInTheDocument()
    })
})
