import {render, screen, waitFor} from '@testing-library/react';
import App from './App';
import {MemoryRouter} from "react-router";
import AuthenticationClient from "./utils/AuthenticationClient";
import userEvent from "@testing-library/user-event";
import TodoList from "./components/Todo/TodoList";

jest.mock('./components/Todo/TodoList');

describe('test app', () => {

    it('redirect to login page when access to root', () => {

        // setup, act
        render(
            <MemoryRouter initialEntries={['/']}>
                <App/>
            </MemoryRouter>
        );

        expect(screen.getByPlaceholderText('USER ID')).toBeInTheDocument();
    });

    it('router to todo list page when succeed login', async () => {

        // setup
        jest.spyOn(AuthenticationClient, 'authenticate').mockResolvedValue(true);
        TodoList.mockImplementation(() => <div>MOCKED TODO LIST</div>);
        render(
            <MemoryRouter initialEntries={['/login']}>
                <App/>
            </MemoryRouter>
        );

        userEvent.type(screen.getByPlaceholderText('USER ID'), 'junhyunny');
        userEvent.type(screen.getByPlaceholderText('PASSWORD'), '123');

        // act
        userEvent.click(screen.getByRole('button', {
            name: 'Submit'
        }));

        // assert
        await waitFor(() => {
            expect(screen.getByText('MOCKED TODO LIST')).toBeInTheDocument();
        });
    });
});
