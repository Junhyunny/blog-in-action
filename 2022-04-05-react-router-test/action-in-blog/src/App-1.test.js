import { render, screen } from '@testing-library/react'
import userEvent from '@testing-library/user-event'

import { MemoryRouter, Route, Routes } from 'react-router-dom'
import App from './App'
import FirstPage from './FirstPage'

it('route FirstPage - 1', () => {
    render(
        <MemoryRouter>
            <Routes>
                <Route path={'/'} element={<App />} />
                <Route path={'/first'} element={<FirstPage />} />
            </Routes>
        </MemoryRouter>
    )

    userEvent.click(screen.getByText('submit'))

    expect(screen.getByText('This is First Page')).toBeInTheDocument()
})
