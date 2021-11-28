import React, { useState } from 'react';

import AddUser from './components/Users/AddUser';
import UserList from "./components/Users/UserList";

function App() {

    const [users, setUsers] = useState([]);

    const changeUsersHandler = (enteredUser) => {
        console.log(enteredUser);
        setUsers((prevState) => {
            setUsers([enteredUser, ...prevState]);
        });
    };

    return (
        <div>
            <AddUser onChangeUsers={changeUsersHandler} />
            <UserList users={users} />
        </div>
    );
}

export default App;
