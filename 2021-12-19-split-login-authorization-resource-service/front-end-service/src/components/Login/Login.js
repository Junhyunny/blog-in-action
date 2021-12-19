import {useState} from "react";
import AuthenticationClient from "../../utils/AuthenticationClient";
import {useNavigate} from "react-router";

const Login = () => {

    const [isValid, setIsValid] = useState(true);
    const [userId, setUserId] = useState('');
    const [password, setPassword] = useState('');

    const navigate = useNavigate();

    const submitHandler = (event) => {
        event.preventDefault();
        if (userId.trim().length <= 0) {
            setIsValid(false);
            return;
        }
        if (password.trim().length <= 0) {
            setIsValid(false);
            return;
        }
        setIsValid(true);
        setUserId('');
        setPassword('');
        AuthenticationClient.authenticate({
            username: userId,
            password: password
        }).then(result => {
            if (result) {
                navigate('/todo');
            }
        });
    };

    const userIdChangeHandler = ({target: {value}}) => {
        setUserId(value);
    };

    const passwordChangeHandler = ({target: {value}}) => {
        setPassword(value);
    };

    return (
        <div>
            <form onSubmit={submitHandler}>
                <input placeholder="USER ID" onChange={userIdChangeHandler} value={userId}/>
                {!isValid && !userId && <p>ID가 유효하지 않습니다.</p>}
                <input placeholder="PASSWORD" onChange={passwordChangeHandler} value={password}/>
                {!isValid && !password && <p>비밀번호가 유효하지 않습니다.</p>}
                <button type="submit">Submit</button>
            </form>
        </div>
    );
};

export default Login;