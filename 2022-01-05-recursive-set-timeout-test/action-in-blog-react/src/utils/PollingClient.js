import axios from "axios";

const polling = (callback, path, config, interval, maxAttempts = -1) => {
    if (maxAttempts === 0) {
        return;
    }
    setTimeout(async () => {
        try {
            const response = await axios.get(`http://localhost:8080${path}`, config);
            callback(response);
        } catch (error) {
            console.error(error);
        }
        polling(callback, path, config, interval, maxAttempts - 1);
    }, interval);
};

const pocPolling = (callback, path, config, interval, maxAttempts = -1) => {
    if (maxAttempts === 0) {
        return;
    }
    setTimeout(async () => {
        try {
            const response = await new Promise((response) => response({data: true}));
            callback(response);
        } catch (error) {
            console.error(error);
        }
        pocPolling(callback, path, config, interval, maxAttempts - 1);
    }, interval);
};

export default {
    polling,
    pocPolling
};