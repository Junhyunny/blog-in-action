export default (func, timeout) => {
    let wait = false;
    return (...args) => {
        if (wait) {
            return;
        }
        setTimeout(() => {
            wait = false;
        }, timeout);
        wait = true;
        func(args);
    };
};