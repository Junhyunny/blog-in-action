import {render, screen, waitFor} from '@testing-library/react';
import App from './App';
import {MemoryRouter} from "react-router";
import * as authentication from "./modules/authentication";
import userEvent from "@testing-library/user-event";
import Main from "./components/Main/Main";
import {Provider} from "react-redux";
import {applyMiddleware, createStore} from "redux";
import modules from "./modules";
import ReduxThunk from "redux-thunk";

jest.mock('./components/Main/Main');

const renderingWithProviderRouter = (component, path, store) => {
    return (
        <Provider store={store}>
            <MemoryRouter initialEntries={path}>
                {component}
            </MemoryRouter>
        </Provider>
    );
};

describe('test app', () => {

    let store;

    afterEach(() => {
        jest.restoreAllMocks();
    });

    beforeEach(() => {
        store = createStore(modules, applyMiddleware(ReduxThunk));
    });

    it('redirect to login page when access to root', () => {

        // setup, act
        render(renderingWithProviderRouter(<App/>, ['/'], store));

        expect(screen.getByPlaceholderText('USER ID')).toBeInTheDocument();
    });

    it('route to todo list page when succeed login', async () => {

        // setup
        jest.spyOn(authentication, 'authenticate').mockReturnValue(() => {
            store.dispatch(authentication.setAuthentication(true));
            return Promise.resolve(true);
        });
        Main.mockImplementation(() => <div>MOCKED MAIN PAGE</div>);
        render(renderingWithProviderRouter(<App/>, ['/login'], store));
        userEvent.type(screen.getByPlaceholderText('USER ID'), 'junhyunny');
        userEvent.type(screen.getByPlaceholderText('PASSWORD'), '123');

        // act
        userEvent.click(screen.getByRole('button', {
            name: 'Submit'
        }));

        // assert
        await waitFor(() => {
            expect(screen.getByText('MOCKED MAIN PAGE')).toBeInTheDocument();
        });
    });

    it('route to login page when have not been authenticated', () => {

        // setup
        Main.mockImplementation(() => <div>MOCKED MAIN PAGE</div>);

        // act
        render(renderingWithProviderRouter(<App/>, ['/main'], store));

        // assert
        expect(screen.getByPlaceholderText('USER ID')).toBeInTheDocument();
    });

    it('route to main page when have been authenticated', async () => {

        // setup
        jest.spyOn(authentication, 'authenticate').mockReturnValue(() => {
            store.dispatch(authentication.setAuthentication(true));
            return Promise.resolve(true);
        });
        Main.mockImplementation(() => <div>MOCKED MAIN PAGE</div>);
        render(renderingWithProviderRouter(<App/>, ['/main'], store));
        userEvent.type(screen.getByPlaceholderText('USER ID'), 'junhyunny');
        userEvent.type(screen.getByPlaceholderText('PASSWORD'), '123');

        // act
        userEvent.click(screen.getByRole('button', {
            name: 'Submit'
        }));

        // assert
        await waitFor(() => {
            expect(screen.getByText('MOCKED MAIN PAGE')).toBeInTheDocument();
        });
    });
});
