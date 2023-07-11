import React, { useEffect, useState } from "react";
import logo from "./second.png";
import "./App.css";
import { Link } from "react-router-dom";

function App() {
  const [state, setState] = useState<string>("");

  useEffect(() => {
    fetch("/api/page-second")
      .then((response) => response.text())
      .then((response) => setState(response));
  }, []);

  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>{state}</p>
        <Link to={"/first"}>Move to First Page</Link>
      </header>
    </div>
  );
}

export default App;
