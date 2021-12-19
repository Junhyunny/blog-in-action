import {render, screen, waitFor} from '@testing-library/react';
import App from './App';
import {MemoryRouter} from "react-router";
import AuthenticationClient from "./utils/AuthenticationClient";
import userEvent from "@testing-library/user-event";
import TodoList from "./components/Todo/TodoList";
import AuthenticateProvider from "./store/AuthenticationProvider";

jest.mock('./components/Todo/TodoList');

const renderingWithProviderRouter = (component, path) => {
    return (
        <AuthenticateProvider>
            <MemoryRouter initialEntries={path}>
                {component}
            </MemoryRouter>
        </AuthenticateProvider>
    );
};

describe('test app', () => {

    afterEach(() => {
        jest.restoreAllMocks();
    });

    it('redirect to login page when access to root', () => {

        // setup, act
        render(renderingWithProviderRouter(<App/>, ['/']));

        expect(screen.getByPlaceholderText('USER ID')).toBeInTheDocument();
    });

    it('route to todo list page when succeed login', async () => {

        // setup
        jest.spyOn(AuthenticationClient, 'authenticate').mockResolvedValue(true);
        TodoList.mockImplementation(() => <div>MOCKED TODO LIST</div>);
        render(renderingWithProviderRouter(<App/>, ['/login']));
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

    it('route to login page when have not been authenticated', () => {

        // setup
        TodoList.mockImplementation(() => <div>MOCKED TODO LIST</div>);

        // act
        render(renderingWithProviderRouter(<App/>, ['/todo']));

        // assert
        expect(screen.getByPlaceholderText('USER ID')).toBeInTheDocument();
    });

    it('route to login page when have been authenticated', async () => {

        // setup
        jest.spyOn(AuthenticationClient, 'authenticate').mockResolvedValue(true);
        TodoList.mockImplementation(() => <div>MOCKED TODO LIST</div>);
        render(renderingWithProviderRouter(<App/>, ['/todo']));
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
