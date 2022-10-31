import React from 'react';
import {render, screen, within} from '@testing-library/react';
import App from './App';

import * as UserRepository from './repository/UserRepository'

describe('App Tests', () => {

    test('사용자 테이블에서 사용자 정보를 볼 수 있다.', async () => {

        jest.spyOn(UserRepository, 'getUsers').mockResolvedValue([
            {name: "Junhyunny", age: 33, sex: "Male", phoneNumber: "010-1234-1234"},
            {name: "Ingang", age: 33, sex: "Female", phoneNumber: "010-1234-4321"},
            {name: "Jua", age: 12, sex: "Female", phoneNumber: "010-1234-1234"}
        ])

        render(<App/>);

        expect(await screen.findByText("Junhyunny")).toBeInTheDocument()
        const {parentElement: firstRowElement} = screen.getByText("Junhyunny");
        const firstRow = within(firstRowElement!)
        expect(firstRow.getByText("33")).toBeInTheDocument()
        expect(firstRow.getByText("Male")).toBeInTheDocument()
        expect(firstRow.getByText("010-1234-1234")).toBeInTheDocument()

        expect(screen.getByText("Ingang")).toBeInTheDocument()
        const {parentElement: secondRowElement} = screen.getByText("Ingang");
        const secondRow = within(secondRowElement!)
        expect(secondRow.getByText("33")).toBeInTheDocument()
        expect(secondRow.getByText("Female")).toBeInTheDocument()
        expect(secondRow.getByText("010-1234-4321")).toBeInTheDocument()

        expect(screen.getByText("Jua")).toBeInTheDocument()
        const {parentElement: thirdRowElement} = screen.getByText("Jua");
        const thirdRow = within(thirdRowElement!)
        expect(thirdRow.getByText("12")).toBeInTheDocument()
        expect(thirdRow.getByText("Female")).toBeInTheDocument()
        expect(thirdRow.getByText("010-1234-1234")).toBeInTheDocument()
    });
})

