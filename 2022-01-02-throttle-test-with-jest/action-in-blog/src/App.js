import {useCallback, useEffect, useState} from "react";
import classes from './App.module.css';
import throttle from "./utils/throttle";

function App() {

    const [normalWidth, setNormalWidth] = useState(300);
    const [throttledWidth, setThrottledWidth] = useState(300);

    const increaseWidth = (prevState) => {
        return prevState + 10 > 1000 ? 1000 : prevState + 10;
    };

    const decreaseWidth = (prevState) => {
        return prevState - 10 < 300 ? 300 : prevState - 10;
    };

    const scrollEventHandler = (element) => {
        let lastScrollTop = 0;
        return () => {
            let scrollTop = document.documentElement.scrollTop;
            if (scrollTop > lastScrollTop) {
                if (element === 'normal') {
                    setNormalWidth(increaseWidth);
                } else {
                    setThrottledWidth(increaseWidth);
                }
            } else {
                if (element === 'normal') {
                    setNormalWidth(decreaseWidth);
                } else {
                    setThrottledWidth(decreaseWidth);
                }
            }
            lastScrollTop = scrollTop <= 0 ? 0 : scrollTop;
        };
    };

    const normalEvent = useCallback(scrollEventHandler('normal'), []);
    const throttledEvent = useCallback(throttle(scrollEventHandler('throttled'), 100), []);

    useEffect(() => {
        window.addEventListener('scroll', normalEvent);
        window.addEventListener('scroll', throttledEvent);
        return () => {
            window.removeEventListener('scroll', normalEvent);
            window.removeEventListener('scroll', throttledEvent);
        };
    }, []);

    return (
        <div className={classes.App}>
            <div className={classes.normalDiv} style={{width: `${normalWidth}px`}}>
                div block normal scroll event
            </div>
            <div className={classes.throttledDiv} style={{width: `${throttledWidth}px`}}>
                div block throttled scroll event
            </div>
        </div>
    );
}

export default App;
