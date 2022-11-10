import React from 'react'
import {fireEvent, render, screen} from '@testing-library/react'
import App from './App'
import axios from "axios";
import {getTestViewPort} from "./hooks/useInViewPort";

test('스크롤 다운 이벤트를 수행하면 다음 리스트를 볼 수 있다.', async () => {
    const spyAxios = jest.spyOn(axios, 'get').mockResolvedValueOnce({
        data: {
            results: [
                {name: '1'},
                {name: '2'},
                {name: '3'},
                {name: '4'},
                {name: '5'},
                {name: '6'},
                {name: '7'},
                {name: '8'},
                {name: '9'},
                {name: '10'},
            ],
        },
    })
    jest.spyOn(axios, 'get').mockResolvedValueOnce({
        data: {
            results: [{name: '11'}, {name: '12'}],
        },
    })
    render(<App/>)
    expect(await screen.findByText('10')).toBeInTheDocument()

    fireEvent.scroll(getTestViewPort(), {target: {scrollY: 500}})

    expect(await screen.findByText('11')).toBeInTheDocument()
    expect(spyAxios).toHaveBeenCalledTimes(2)
    expect(spyAxios).toHaveBeenNthCalledWith(1, 'https://pokeapi.co/api/v2/pokemon/?offset=0&limit=10')
    expect(spyAxios).toHaveBeenNthCalledWith(2, 'https://pokeapi.co/api/v2/pokemon/?offset=1&limit=10')
})
