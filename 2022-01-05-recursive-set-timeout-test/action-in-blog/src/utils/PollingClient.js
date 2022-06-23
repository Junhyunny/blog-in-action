import axios from "axios";

const polling = (callback, path, config, interval) => {
    setTimeout(async () => {
        try {
            console.log(5);
            const response = await axios.get(`http://localhost:8080${path}`, config);
            console.log(6);
            callback(response);
        } catch (error) {
            console.error(error);
        }
        polling(callback, path, config, interval);
    }, interval);
};

const pocPolling = (callback, path, config, interval) => {
    setTimeout(async () => {
        try {
            console.log(3);
            const response = await new Promise((response) => response({data: true}));
            console.log(4);
            callback(response);
        } catch (error) {
            console.error(error);
        }
        pocPolling(callback, path, config, interval);
    }, interval);
};

export default {
    polling,
    pocPolling
};