import React, { useEffect, useState } from "react";
import logo from "./first.png";
import "./App.css";
import { Link } from "react-router-dom";

function App() {
  const [state, setState] = useState<string>("");

  useEffect(() => {
    fetch("/api/page-first")
      .then((response) => response.text())
      .then((response) => setState(response));
  }, []);

  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>{state}</p>
        <Link to={"/second"}>Move to Second Page</Link>
      </header>
    </div>
  );
}

export default App;
