import App from "./App";
import {act, render, screen} from "@testing-library/react";
import axios from "axios";
import userEvent from "@testing-library/user-event";

describe('test debounce', () => {

    describe('test rendering elements', () => {

        it('exists input box for search and message when rendered', () => {

            // setup, act
            render(<App/>);

            // assert
            expect(screen.getByPlaceholderText('검색어')).toBeInTheDocument()
            expect(screen.getByText('현재 API 호출 횟수 = 0')).toBeInTheDocument();
        });
    });

    describe('test user interaction', () => {

        it('call axios get method one time when typed some keyword', () => {

            // setup
            jest.useFakeTimers();
            const spyAxios = jest.spyOn(axios, 'get').mockResolvedValue({data: {}});

            // act
            render(<App/>);
            userEvent.type(screen.getByPlaceholderText('검색어'), 'Junhyunny');
            act(() => {
                jest.advanceTimersByTime(500);
            });

            // assert
            expect(spyAxios).toHaveBeenNthCalledWith(1, 'http://localhost:8080/search', {
                params: {
                    keyword: 'Junhyunny'
                }
            });
            expect(screen.getByText('현재 API 호출 횟수 = 1')).toBeInTheDocument();
        });
    });
});