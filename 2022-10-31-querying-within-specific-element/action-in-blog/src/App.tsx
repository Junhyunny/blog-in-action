import React, {useEffect, useState} from 'react';
import {getUsers, User} from "./repository/UserRepository";

import './App.css'

function App() {

    const [users, setUsers] = useState<User[]>([])

    useEffect(() => {
        getUsers().then((userList) => {
            setUsers(userList)
        })
    }, [])

    return (
        <div>
            <h3>사용자 테이블</h3>
            <table>
                <thead>
                <tr>
                    <th>이름</th>
                    <th>나이</th>
                    <th>성별</th>
                    <th>휴대폰번호</th>
                </tr>
                </thead>
                <tbody>
                {
                    users.map(user => (
                        <tr key={user.name}>
                            <td>{user.name}</td>
                            <td>{user.age}</td>
                            <td>{user.sex}</td>
                            <td>{user.phoneNumber}</td>
                        </tr>
                    ))
                }
                </tbody>
            </table>
        </div>
    );
}

export default App;
