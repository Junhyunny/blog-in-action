import logo from './logo.svg';
import './App.css';

import {useEffect} from "react";

import axios from "axios";

function App() {

    useEffect(() => {
        axios.get(`/api/v2/pokemon?limit=100&offset=0`)
            .then(({data}) => console.log('success : ' + data))
    }, [])

    return (
        <div className="App">
            <header className="App-header">
                <img src={logo} className="App-logo" alt="logo"/>
                <p>
                    Edit <code>src/App.js</code> and save to reload.
                </p>
                <a
                    className="App-link"
                    href="https://reactjs.org"
                    target="_blank"
                    rel="noopener noreferrer"
                >
                    Learn React
                </a>
            </header>
        </div>
    );
}

export default App;
