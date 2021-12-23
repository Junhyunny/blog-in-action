import classes from './Login.module.css';
import {useState} from "react";
import {useNavigate} from "react-router";
import {useDispatch} from "react-redux";
import {authenticate} from "../../modules/authentication";

const Login = () => {

    const [isValid, setIsValid] = useState(true);
    const [userId, setUserId] = useState('');
    const [password, setPassword] = useState('');

    const dispatch = useDispatch();
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
        dispatch(authenticate({
            username: userId,
            password: password
        })).then(result => {
            if (result) {
                navigate('/main');
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
        <div className={classes.login}>
            <form className={classes.control} onSubmit={submitHandler}>
                <div>
                    <input placeholder="USER ID" onChange={userIdChangeHandler} value={userId}/>
                </div>
                <div>
                    {!isValid && !userId && <label>ID가 유효하지 않습니다.</label>}
                </div>
                <div>
                    <input placeholder="PASSWORD" onChange={passwordChangeHandler} value={password} type="password"/>
                </div>
                <div>
                    {!isValid && !password && <label>비밀번호가 유효하지 않습니다.</label>}
                </div>
                <button type="submit">Submit</button>
            </form>
        </div>
    );
};

export default Login;