import AuthenticationContext from "./AuthenticationContext";
import {useReducer} from "react";

const defaultAuthenticateState = {
    authenticate: localStorage.getItem('access_token') ? true : false
};

const authenticateReducer = (state, action) => {
    if (action.type === 'LOGIN') {
        return {
            authenticate: true
        };
    } else if (action.type === 'LOGOUT') {
        return {
            authenticate: false
        };
    }
    return defaultAuthenticateState;
};

const AuthenticateProvider = ({children}) => {

    const [authenticateState, dispatchAuthenticationAction] = useReducer(authenticateReducer, defaultAuthenticateState);

    const setAuthenticate = (isAuthenticated) => {
        dispatchAuthenticationAction({
            type: isAuthenticated ? 'LOGIN' : 'LOGOUT'
        });
    };

    const authenticateContext = {
        authenticate: authenticateState.authenticate,
        setAuthenticate,
    };

    return (
        <AuthenticationContext.Provider value={authenticateContext}>
            {children}
        </AuthenticationContext.Provider>
    );
}

export default AuthenticateProvider;