import {render, screen, waitFor} from "@testing-library/react";
import userEvent from "@testing-library/user-event";

import {MemoryRouter, Route, Routes} from "react-router-dom";
import axios from "axios";

import SecondAuthConfirm from "./SecondAuthConfirm";
import InfoPage from "../Info/InfoPage";
import ErrorPage from "../Error/ErrorPage";

jest.mock('../Info/InfoPage');
jest.mock('../Error/ErrorPage');

describe('SecondAuthConfirm Component Test', () => {

    const secondAuthConfirmWithRouter = (initialEntries) => {
        return (
            <MemoryRouter initialEntries={initialEntries}>
                <Routes>
                    <Route path="/confirm" element={<SecondAuthConfirm/>}/>
                    <Route path="/info" element={<InfoPage/>}/>
                    <Route path="/error" element={<ErrorPage/>}/>
                </Routes>
            </MemoryRouter>
        );
    }

    beforeEach(() => {
        jest.restoreAllMocks();
    });

    describe('rendering test', () => {

        it('exists elements when render', () => {

            render(secondAuthConfirmWithRouter(['/confirm']));

            expect(screen.getByText('Junhyunny 님이 맞습니까?')).toBeInTheDocument();
            expect(screen.getByRole('button', {
                name: '맞습니다.'
            })).toBeInTheDocument();
            expect(screen.getByRole('button', {
                name: '아닙니다.'
            })).toBeInTheDocument();
        });

    });

    describe('user interaction test', () => {

        it('when click "맞습니다." button then call post request of axios', () => {

            const spyAxios = jest.spyOn(axios, 'post').mockResolvedValue({
                data: true
            });

            render(secondAuthConfirmWithRouter(['/confirm']));

            userEvent.click(screen.getByRole('button', {
                name: '맞습니다.'
            }));

            expect(spyAxios).toHaveBeenNthCalledWith(1, 'http://localhost:8080/second-auth', {}, {
                params: {
                    name: 'Junhyunny'
                }
            });
        });

        it('given api result true when click "맞습니다." button then route to info page', async () => {

            jest.spyOn(axios, 'post').mockResolvedValue({
                data: true
            });

            InfoPage.mockImplementation(() => <div>This is Info Page</div>);

            render(secondAuthConfirmWithRouter(['/confirm']));

            userEvent.click(screen.getByRole('button', {
                name: '맞습니다.'
            }));

            await waitFor(() => {
                expect(screen.getByText('This is Info Page')).toBeInTheDocument();
            });
        });

        it('given api result false when click "맞습니다." button then route to error page', async () => {

            jest.spyOn(axios, 'post').mockResolvedValue({
                data: false
            });

            ErrorPage.mockImplementation(() => <div>This is Error Page</div>);

            render(secondAuthConfirmWithRouter(['/confirm']));

            userEvent.click(screen.getByRole('button', {
                name: '맞습니다.'
            }));

            await waitFor(() => {
                expect(screen.getByText('This is Error Page')).toBeInTheDocument();
            });
        });

        it('given api result false when click "아닙니다." button then route to info page', async () => {

            InfoPage.mockImplementation(() => <div>This is Info Page</div>);

            render(secondAuthConfirmWithRouter(['/confirm']));

            userEvent.click(screen.getByRole('button', {
                name: '아닙니다.'
            }));

            await waitFor(() => {
                expect(screen.getByText('This is Info Page')).toBeInTheDocument();
            });
        });

    });

});