import {render, screen} from "@testing-library/react";

import React from "react";

import App from "./App";

import PokeProvider, {PokeContext} from "./store/PokeProvider";
import UIProvider, {UIContext} from "./store/UIProvider";

jest.mock('react', () => ({
    ...jest.requireActual('react'), useContext: (context) => {
        console.log(context)
        if (typeof context === 'PokeContext') {
            return {
                state: {
                    pokemons: [{name: 'bulbasaur'}, {name: 'ivysaur'}]
                }, dispatch: jest.fn()
            }
        } else if (typeof context === 'UIContext') {
            return {
                state: {
                    isLoading: false
                }, dispatch: jest.fn()
            }
        }
    },
}))

describe('App Tests', () => {

    test('renders pokemon lists', () => {

        render(<UIProvider>
            <PokeProvider>
                <App/>
            </PokeProvider>
        </UIProvider>)

        expect(screen.getByText('bulbasaur')).toBeInTheDocument()
        expect(screen.getByText('ivysaur')).toBeInTheDocument()
    });
})

