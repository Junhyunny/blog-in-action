import React, { useState, useEffect } from 'react';

const AuthContext = React.createContext({
    isLoggedIn: false,
    onLogout: () => { },
    onLogin: (email, password) => { }
});

export const AuthContextProvider = (props) => {

    const [isLoggedIn, setIsLoggedIn] = useState(false);

    useEffect(() => {
        const storedUserLoginInfo = localStorage.getItem('isLoggedIn');
        if (storedUserLoginInfo === '1') {
            setIsLoggedIn(true);
        }
    }, [isLoggedIn]);

    const loginHandler = () => {
        localStorage.setItem('isLoggedIn', '1');
        setIsLoggedIn(true);
    };

    const logoutHandler = () => {
        localStorage.setItem('isLoggedIn', '0');
        setIsLoggedIn(false);
    };

    return (
        <AuthContext.Provider value={{
            isLoggedIn: isLoggedIn,
            onLogin: loginHandler,
            onLogout: logoutHandler
        }}>
            {props.children}
        </AuthContext.Provider>
    );
};

export default AuthContext;