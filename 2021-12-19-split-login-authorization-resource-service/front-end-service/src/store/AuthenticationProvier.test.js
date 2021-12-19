import AuthenticateProvider from "./AuthenticationProvider";
import {render, screen} from "@testing-library/react";
import App from "../App";
import AuthenticationContext from "./AuthenticationContext";
import {useEffect} from "react";

jest.mock('../App');

describe('test authentication provider', () => {

    afterEach(() => {
        jest.restoreAllMocks();
    });

    it('render children component', () => {

        // set
        App.mockImplementation(() => {
            return (
                <>This is mocked app.</>
            );
        });

        // act
        render(
            <AuthenticateProvider>
                <App/>
            </AuthenticateProvider>
        )

        // verify
        expect(screen.getByText('This is mocked app.')).toBeInTheDocument();
    });

    it('re-render component when context change', () => {

        // set
        App.mockImplementation(({authenticate, setAuthenticate}) => {
            useEffect(() => {
                if (!authenticate) {
                    setAuthenticate(true);
                }
            }, []);
            return (
                <>This is mocked app. {authenticate ? 're-rendering' : 'first rendering'}</>
            );
        });

        // act
        render(
            <AuthenticateProvider>
                <AuthenticationContext.Consumer>
                    {(value) => <App authenticate={value.authenticate} setAuthenticate={value.setAuthenticate}/>}
                </AuthenticationContext.Consumer>
            </AuthenticateProvider>
        );

        // verify
        expect(screen.getByText('This is mocked app. re-rendering')).toBeInTheDocument();
    });
});