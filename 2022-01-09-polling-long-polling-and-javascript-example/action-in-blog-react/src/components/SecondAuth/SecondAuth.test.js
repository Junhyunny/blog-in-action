import {render, screen, waitFor} from "@testing-library/react";

import axios from "axios";
import {MemoryRouter, Route, Routes} from "react-router-dom";

import Main from "../Main/Main";
import ErrorPage from "../Error/ErrorPage";
import SecondAuth from "./SecondAuth";

jest.mock('../Main/Main');
jest.mock('../Error/ErrorPage');

describe('SecondAuth Component Test', () => {

    const secondAuthWithRouter = (initialEntries) => {
        return (
            <MemoryRouter initialEntries={initialEntries}>
                <Routes>
                    <Route path="/main" element={<Main/>}/>
                    <Route path="/second-auth" element={<SecondAuth/>}/>
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

            render(secondAuthWithRouter(['/second-auth']));

            expect(screen.getByText('Junhyunny 님, 휴대폰에서 확인 버튼을 눌러주세요.')).toBeInTheDocument();
        });

    });

    describe('user interaction test', () => {

        it('given api result true when waiting confirm then route to main page', async () => {

            jest.spyOn(axios, 'get').mockResolvedValue({
                data: true
            });

            Main.mockImplementation(() => <div>This is Main Page</div>);

            render(secondAuthWithRouter(['/second-auth']));

            await waitFor(() => {
                expect(screen.getByText('This is Main Page')).toBeInTheDocument();
            });
        });

        it('given api result false when waiting confirm then route to error page', async () => {

            jest.spyOn(axios, 'get').mockResolvedValue({
                data: false
            });

            ErrorPage.mockImplementation(() => <div>This is Error Page</div>);

            render(secondAuthWithRouter(['/second-auth']));

            await waitFor(() => {
                expect(screen.getByText('This is Error Page')).toBeInTheDocument();
            });
        });
    });

});