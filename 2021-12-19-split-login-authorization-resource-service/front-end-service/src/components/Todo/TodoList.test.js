import {render, screen, waitFor} from "@testing-library/react";
import TodoList from "./TodoList";
import HttpClient from "../../utils/HttpClient";

describe('test todo list', () => {

    describe('test rendering elements', () => {

        it('exists user info when rendering finish', async () => {

            // setup
            jest.spyOn(HttpClient, 'get').mockResolvedValue({
                id: 'Junhyunny',
                authorities: ['ADMIN']
            });

            // act
            render(<TodoList/>);

            // assert
            await waitFor(() => {
                expect(screen.getByText('사용자 Junhyunny (관리자)')).toBeInTheDocument();
            });
        });
    });
});