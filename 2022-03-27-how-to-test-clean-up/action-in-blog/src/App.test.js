import {render, screen} from "@testing-library/react";
import userEvent from "@testing-library/user-event";

import App from "./App";
import * as EventAPI from "./EventAPI";

describe('App js', () => {

    it('remove click event when componentWillUnmount method', () => {

        const spyAddClickEvent = jest.spyOn(EventAPI, 'addClickEvent')
        const spyRemoveClickEvent = jest.spyOn(EventAPI, 'removeClickEvent')
        const {unmount} = render(<App/>)
        userEvent.click(screen.getByText('Click me'))

        unmount()

        expect(spyAddClickEvent).toHaveBeenCalledTimes(1)
        expect(spyRemoveClickEvent).toHaveBeenCalledTimes(1)
    })
})