import AuthenticationClient from "../utils/AuthenticationClient";

const AUTHENTICATION_SUCCESS = 'authentication/AUTHENTICATION_SUCCESS';
const AUTHENTICATION_FAILURE = 'authentication/AUTHENTICATION_FAILURE';

const initialState = {
    isAuthenticate: localStorage.getItem('access_token') ? true : false
};

export const setAuthentication = (isAuthenticate) => {
    return {type: isAuthenticate ? AUTHENTICATION_SUCCESS : AUTHENTICATION_FAILURE};
}

export const authenticationFailure = () => {
    return {type: AUTHENTICATION_FAILURE};
}

export const authenticate = (params) => (dispatch) => {
    return AuthenticationClient.authenticate(params)
        .then(result => {
            dispatch(setAuthentication(result));
            return result;
        }).catch(error => {
            dispatch(authenticationFailure());
            return error;
        });
}

export default (state = initialState, action) => {
    switch (action.type) {
        case AUTHENTICATION_SUCCESS:
            return {
                isAuthenticate: true
            };
        case AUTHENTICATION_FAILURE:
            return {
                isAuthenticate: false
            };
        default:
            return state;
    }
};