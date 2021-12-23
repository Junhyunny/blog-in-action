import {render, screen, waitFor} from '@testing-library/react';
import userEvent from "@testing-library/user-event";

import {MemoryRouter} from "react-router";

import {applyMiddleware, combineReducers, createStore} from "redux";
import {Provider} from "react-redux";
import ReduxThunk from "redux-thunk";

import authenticationReducer, * as authentication from "../../modules/authentication";
import Login from "./Login";

const renderingWithMemoryRouterAndProvider = (component, path) => {
    const store = createStore(combineReducers({authenticationReducer}), applyMiddleware(ReduxThunk));
    return (
        <Provider store={store}>
            <MemoryRouter initialEntries={path}>
                {component}
            </MemoryRouter>
        </Provider>
    );
};

describe('test login', () => {

    describe('test rendering elements', () => {

        it('render elements when rendering', () => {

            // setup, action
            render(renderingWithMemoryRouterAndProvider(<Login/>, ['/']));

            // assert
            expect(screen.getByPlaceholderText('USER ID')).toBeInTheDocument();
            expect(screen.getByPlaceholderText('PASSWORD')).toBeInTheDocument();
            expect(screen.getByRole('button', {
                name: 'Submit'
            })).toBeInTheDocument();
            expect(screen.queryByText('ID가 유효하지 않습니다.')).not.toBeInTheDocument();
            expect(screen.queryByText('비밀번호가 유효하지 않습니다.')).not.toBeInTheDocument();
        });
    });

    describe('test user interaction', () => {

        it('exists error message when click submit button with empty inputs', () => {

            // setup
            render(renderingWithMemoryRouterAndProvider(<Login/>, ['/']));

            // action
            userEvent.click(screen.getByRole('button', {
                name: 'Submit'
            }));

            // assert
            expect(screen.getByText('ID가 유효하지 않습니다.')).toBeInTheDocument();
            expect(screen.getByText('비밀번호가 유효하지 않습니다.')).toBeInTheDocument();
        });

        it('call authenticate method with params and clear inputs when click submit button', async () => {

            // setup
            const spyAuthenticationModule = jest.spyOn(authentication, 'authenticate').mockReturnValue(() => {
                return Promise.resolve(true);
            });
            render(renderingWithMemoryRouterAndProvider(<Login/>, ['/']));
            userEvent.type(screen.getByPlaceholderText('USER ID'), 'junhyunny');
            userEvent.type(screen.getByPlaceholderText('PASSWORD'), '123');

            // action
            userEvent.click(screen.getByRole('button', {
                name: 'Submit'
            }));

            // assert
            await waitFor(() => {
                expect(spyAuthenticationModule).toHaveBeenNthCalledWith(1, {
                    username: 'junhyunny',
                    password: '123'
                });
            });
            expect(screen.getByPlaceholderText('USER ID').value).toEqual('');
            expect(screen.getByPlaceholderText('PASSWORD').value).toEqual('');
        });
    });
});