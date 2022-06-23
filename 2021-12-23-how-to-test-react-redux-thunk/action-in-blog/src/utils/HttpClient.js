import axios from "axios";

const get = async (path, params) => {
    const tokenType = localStorage.getItem('token_type');
    const accessToken = localStorage.getItem('access_token');
    let config;
    if (accessToken) {
        config = {
            headers: {
                Authorization: `${tokenType} ${accessToken}`
            }
        };
    }
    config = {
        ...config,
        params
    }
    const {data} = await axios.get(`http://localhost:8081${path}`, config);
    return data;
}

export default {
    get
}