import axios from "axios";
import {useNavigate} from "react-router";
import {useEffect} from "react";
import PollingClient from "../../utils/PollingClient";

const SecondAuth = () => {

    const name = 'Junhyunny';

    const navigator = useNavigate();

    useEffect(() => {
        axios.get('http://localhost:8080/second-auth', {
            params: {
                name
            }
        }).then(({data}) => {
            if (data && data !== 'false') {
                navigator('/main');
                return;
            }
            navigator('/error');
        });
    }, []);

    return (
        <div>
            <p>{name} 님, 휴대폰에서 확인 버튼을 눌러주세요.</p>
        </div>
    );

};

export default SecondAuth;