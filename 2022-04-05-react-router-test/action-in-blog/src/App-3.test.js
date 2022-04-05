import { render, screen } from '@testing-library/react'
import userEvent from '@testing-library/user-event'

import App from './App'

const mockNavigate = jest.fn()
jest.mock('react-router-dom', () => ({
    ...jest.requireActual('react-router-dom'),
    useNavigate: () => mockNavigate,
}))

it('route FirstPage - 3', () => {
    render(<App />)

    userEvent.click(screen.getByText('submit'))

    expect(mockNavigate).toHaveBeenNthCalledWith(1, '/first')
})
