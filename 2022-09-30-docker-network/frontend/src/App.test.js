import {render, screen} from "@testing-library/react";
import App from "./App";

describe('User Journey Test', () => {

    test('render TODO LIST title', () => {
        render(<App/>)

        expect(screen.getByText('TODO LIST')).toBeInTheDocument()
    })
})
