import { useCallback, useEffect } from "react";
import { throttle } from "./utils/throttle";
import classes from "./App.module.css";

function App() {
  const increaseWidth = (element) => {
    let prevState = element.offsetWidth;
    element.style.width = `${prevState + 10 > 1000 ? 1000 : prevState + 10}px`;
  };

  const decreaseWidth = (element) => {
    let prevState = element.offsetWidth;
    element.style.width = `${prevState - 10 < 300 ? 300 : prevState - 10}px`;
  };

  const scrollEventHandler = (elementId) => {
    let lastScrollTop = 0;
    return () => {
      let scrollTop = document.documentElement.scrollTop;
      const element = document.getElementById(elementId);
      if (scrollTop > lastScrollTop) {
        increaseWidth(element);
      } else {
        decreaseWidth(element);
      }
      lastScrollTop = scrollTop <= 0 ? 0 : scrollTop;
    };
  };

  const normalEvent = useCallback(scrollEventHandler("normal"), []);
  const throttledEvent = useCallback(
    throttle(scrollEventHandler("throttled"), 100),
    [],
  );

  useEffect(() => {
    window.addEventListener("scroll", normalEvent);
    window.addEventListener("scroll", throttledEvent);
    return () => {
      window.removeEventListener("scroll", normalEvent);
      window.removeEventListener("scroll", throttledEvent);
    };
  }, []);

  return (
    <div className={classes.App}>
      <div id="normal" className={classes.normalDiv}>
        div block normal scroll event
      </div>
      <div id="throttled" className={classes.throttledDiv}>
        div block throttled scroll event
      </div>
    </div>
  );
}

export default App;
