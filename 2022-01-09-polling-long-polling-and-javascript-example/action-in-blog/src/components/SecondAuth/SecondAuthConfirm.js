import axios from "axios";
import {useNavigate} from "react-router";

const SecondAuthConfirm = () => {

    const name = 'Junhyunny';

    const navigator = useNavigate();

    const confirmHandler = () => {
        axios.post('http://localhost:8080/second-auth', {}, {
            params: {
                name
            }
        }).then(({data}) => {
            if (data && data !== 'false') {
                navigator('/info');
                return;
            }
            navigator('/error');
        });
    };

    const cancelHandler = () => {
        navigator('/info');
    };

    return (
        <div>
            <div>
                <p>{name} 님이 맞습니까?</p>
            </div>
            <div>
                <button onClick={cancelHandler}>
                    아닙니다.
                </button>
                <button onClick={confirmHandler}>
                    맞습니다.
                </button>
            </div>
        </div>
    );

};

export default SecondAuthConfirm;