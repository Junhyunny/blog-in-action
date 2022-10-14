import {createContext, useReducer} from "react";

const initialState = {
    isLoading: false
}

const reducer = (state, action) => {
    switch (action.type) {
        case "START_LOADING":
            return {
                isLoading: true
            }
        case "END_LOADING":
            return {
                isLoading: false
            }
        default:
            throw new Error(`not supported action type: ${action.type}`)
    }
}

export const UIContext = createContext(null)

const UIProvider = ({children}) => {

    const [state, dispatch] = useReducer(reducer, initialState)

    const middleware = async (action) => {
        if (typeof action === 'function') {
            await action(dispatch, state)
        } else {
            dispatch(action)
        }
    }

    return <UIContext.Provider value={{state, dispatch: middleware}}>
        {children}
    </UIContext.Provider>
}

export default UIProvider;