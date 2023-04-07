import React from "react";
import "./App.css";
import { getUserTransactionId } from "./uuid";
import { overrideDataLayer } from "./gtm";

function App() {
  overrideDataLayer(getUserTransactionId());

  const clickEventHandler = () => {
    alert("CLICK");
  };

  const customEventHandler = () => {
    alert("CUSTOM EVENT");
    window.dataLayer.push({
      event: "custom_event",
      customData: `custom_event_data_${Math.random()}`,
    });
  };

  return (
    <div className="App">
      <div id="normal-click" className="square" onClick={clickEventHandler}>
        Click Event
      </div>
      <button className="square" onClick={customEventHandler}>
        Send Custom Event
      </button>
    </div>
  );
}

export default App;
