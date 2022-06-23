import {render, screen} from "@testing-library/react";
import {MemoryRouter} from "react-router";
import App from "./App";
import SecondAuth from "./components/SecondAuth/SecondAuth";
import SecondAuthConfirm from "./components/SecondAuth/SecondAuthConfirm";
import Main from "./components/Main/Main";
import ErrorPage from "./components/Error/ErrorPage";
import InfoPage from "./components/Info/InfoPage";

jest.mock('./components/SecondAuth/SecondAuth');
jest.mock('./components/SecondAuth/SecondAuthConfirm');
jest.mock('./components/Main/Main');
jest.mock('./components/Info/InfoPage');
jest.mock('./components/Error/ErrorPage');

describe('App component test', () => {

    const appComponentWithMemoryRouting = (initialEntries) => {
        return (
            <MemoryRouter initialEntries={initialEntries}>
                <App/>
            </MemoryRouter>
        );
    }

    it('route to second-auth component when access to root', () => {

        SecondAuth.mockImplementation(() => <div>This is SecondAuth Component</div>);

        render(
            appComponentWithMemoryRouting(['/'])
        );

        expect(screen.getByText('This is SecondAuth Component')).toBeInTheDocument();
    });

    it('route to second-auth component when access to /second-auth', () => {

        SecondAuth.mockImplementation(() => <div>This is SecondAuth Component</div>);

        render(
            appComponentWithMemoryRouting(['/second-auth'])
        );

        expect(screen.getByText('This is SecondAuth Component')).toBeInTheDocument();
    });

    it('route to confirm component when access to /confirm', () => {

        SecondAuthConfirm.mockImplementation(() => <div>This is Confirm Component</div>);

        render(
            appComponentWithMemoryRouting(['/confirm'])
        );

        expect(screen.getByText('This is Confirm Component')).toBeInTheDocument();
    });

    it('route to main component when access to /main', () => {

        Main.mockImplementation(() => <div>This is Main Component</div>);

        render(
            appComponentWithMemoryRouting(['/main'])
        );

        expect(screen.getByText('This is Main Component')).toBeInTheDocument();
    });

    it('route to info component when access to /info', () => {

        InfoPage.mockImplementation(() => <div>This is Info Page</div>);

        render(
            appComponentWithMemoryRouting(['/info'])
        );

        expect(screen.getByText('This is Info Page')).toBeInTheDocument();
    });

    it('route to error component when access to /error', () => {

        ErrorPage.mockImplementation(() => <div>This is Error Page</div>);

        render(
            appComponentWithMemoryRouting(['/error'])
        );

        expect(screen.getByText('This is Error Page')).toBeInTheDocument();
    });
});