import { render, screen } from '@testing-library/react'
import userEvent from '@testing-library/user-event'

import { MemoryRouter, Route, Routes } from 'react-router-dom'
import App from './App'

it('route FirstPage - 2', () => {
    const StubPage = jest.fn().mockImplementation(() => <div>This is stubbing page</div>)
    render(
        <MemoryRouter>
            <Routes>
                <Route path={'/'} element={<App />} />
                <Route path={'/first'} element={<StubPage />} />
            </Routes>
        </MemoryRouter>
    )

    userEvent.click(screen.getByText('submit'))

    expect(screen.getByText('This is stubbing page')).toBeInTheDocument()
})
