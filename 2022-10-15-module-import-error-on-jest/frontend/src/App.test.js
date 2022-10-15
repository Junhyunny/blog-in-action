import {render, screen} from '@testing-library/react';
import App from './App';
import axios from "axios";

test('renders learn react link', () => {
    const spy = jest.spyOn(axios, 'get').mockResolvedValue({data: 'Hello World'});

    render(<App/>);

    const linkElement = screen.getByText(/learn react/i);
    expect(linkElement).toBeInTheDocument();
    expect(spy).toHaveBeenCalledTimes(1);
});
