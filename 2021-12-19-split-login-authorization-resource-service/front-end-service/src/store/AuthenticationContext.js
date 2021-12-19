import React from "react";

const AuthenticationContext = React.createContext({
    authenticate: false,
    setAuthenticate: (authenticate) => {},
});

export default AuthenticationContext;
