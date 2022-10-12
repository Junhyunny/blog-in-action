import {createContext, useReducer} from "react";

const initialState = {
    pokemons: []
}

const reducer = (state, action) => {
    switch (action.type) {
        case "SET_POKEMONS" :
            return {
                pokemons: action.pokemons
            }
        default:
            throw new Error();
    }
};

export const PokeContext = createContext(null);

const PokeProvider = ({children}) => {

    const [state, dispatch] = useReducer(reducer, initialState)

    const middleware = async (action) => {
        if (typeof action === 'function') {
            action(dispatch, state)
        } else {
            dispatch(action)
        }
    }

    return <PokeContext.Provider value={{state, dispatch: middleware}}>
        {children}
    </PokeContext.Provider>
}

export default PokeProvider;