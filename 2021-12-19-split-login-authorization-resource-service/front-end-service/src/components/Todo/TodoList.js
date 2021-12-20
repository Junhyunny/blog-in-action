import {useEffect, useState} from "react";
import HttpClient from "../../utils/HttpClient";
import classes from './TodoList.module.css';

const TodoList = () => {

    const [user, setUser] = useState({
        id: '',
        authorities: []
    });

    useEffect(() => {
        const username = localStorage.getItem('username');
        HttpClient.get(`/member/${username}`, {})
            .then(data => {
                setUser(data);
            })
    }, []);

    return (
        <div className={classes.todoList}>
            사용자 {user.id} ({user.authorities && user.authorities[0] === 'ADMIN' ? '관리자' : '일반'})
        </div>
    );
};

export default TodoList;