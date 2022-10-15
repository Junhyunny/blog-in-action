import {render, screen} from "@testing-library/react";

import React from "react";

import App from "./App";
import react from "react";

describe('App Tests', () => {

    test('renders pokemons', () => {
        jest.spyOn(react, 'useContext').mockReturnValue({
            state: {
                pokemons: [{name: 'bulbasaur'}, {name: 'ivysaur'}],
                isLoading: false
            }
        })

        render(<App/>)

        expect(screen.getByText('bulbasaur')).toBeInTheDocument()
        expect(screen.getByText('ivysaur')).toBeInTheDocument()
    });

    test('renders other pokemons', () => {
        jest.spyOn(react, 'useContext').mockReturnValue({
            state: {
                pokemons: [{name: 'kangaskhan'}, {name: 'horsea'}],
                isLoading: false
            }
        })

        render(<App/>)

        expect(screen.getByText('kangaskhan')).toBeInTheDocument()
        expect(screen.getByText('horsea')).toBeInTheDocument()
    });
})