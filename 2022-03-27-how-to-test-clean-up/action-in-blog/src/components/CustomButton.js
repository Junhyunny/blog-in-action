import { useEffect } from "react";
import { addClickEvent, removeClickEvent } from "../utils/event-listeners";

function CustomButton(props) {
  const clickHandler = () => {
    console.log(`this click handler is added when count is ${props.count}`);
  };

  useEffect(() => {
    addClickEvent(clickHandler);
    return () => {
      removeClickEvent(clickHandler);
    };
  }, []);

  return <button onClick={props.onClick}>Custom Click me</button>;
}

export default CustomButton;
