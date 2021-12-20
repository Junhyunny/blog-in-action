import axios from "axios";

const authenticate = async (params) => {
    let result = true;
    try {
        const {data} = await axios.post('http://localhost:8080/oauth/token', {}, {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            auth: {
                username: 'CLIENT_ID',
                password: 'CLIENT_SECRET'
            },
            params: {
                ...params,
                grant_type: 'password'
            }
        });
        localStorage.setItem('username', params['username']);
        localStorage.setItem('access_token', data['access_token']);
        localStorage.setItem('refresh_token', data['refresh_token']);
        localStorage.setItem('token_type', data['token_type']);
    } catch (error) {
        localStorage.setItem('username', '');
        localStorage.setItem('access_token', '');
        localStorage.setItem('refresh_token', '');
        localStorage.setItem('token_type', '');
        result = false;
    }
    return result;
};

export default {
    authenticate
};