import {render, screen, waitFor} from '@testing-library/react';
import Login from "./Login";
import userEvent from "@testing-library/user-event";
import AuthenticationClient from "../../utils/AuthenticationClient";
import {MemoryRouter} from "react-router";

const renderingMemoryRouter = (component, path) => {
    return (
        <MemoryRouter initialEntries={path}>
            {component}
        </MemoryRouter>
    );
};

describe('test login', () => {

    describe('test rendering elements', () => {

        it('render elements when rendering', () => {

            // setup, action
            render(renderingMemoryRouter(<Login/>, ['/']));

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
            render(renderingMemoryRouter(<Login/>, ['/']));

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
            const spyAuthenticationClient = jest.spyOn(AuthenticationClient, 'authenticate').mockResolvedValue(true);
            render(renderingMemoryRouter(<Login/>, ['/']));
            userEvent.type(screen.getByPlaceholderText('USER ID'), 'junhyunny');
            userEvent.type(screen.getByPlaceholderText('PASSWORD'), '123');

            // action
            userEvent.click(screen.getByRole('button', {
                name: 'Submit'
            }));

            // assert
            await waitFor(() => {
                expect(spyAuthenticationClient).toHaveBeenNthCalledWith(1, {
                    username: 'junhyunny',
                    password: '123'
                });
            });
            expect(screen.getByPlaceholderText('USER ID').value).toEqual('');
            expect(screen.getByPlaceholderText('PASSWORD').value).toEqual('');
        });
    });
});