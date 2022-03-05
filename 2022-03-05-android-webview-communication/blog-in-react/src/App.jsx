import logo from './logo.svg'
import './App.css'
import {useEffect, useState} from "react";

function App() {

    const [messageFromAndroid, setMessageFromAndroid] = useState('Hello Vite + React!');

    useEffect(() => {

        const eventFromAndroid = async (event) => {
            setMessageFromAndroid(event.detail.data);
        }

        window.addEventListener('javascriptFunction', eventFromAndroid);

        if (window.android) {
            window.android.showToastMessage("Hello Native Callback");
            window.android.callJavaScriptFunction();
        }

        return () => {
            window.removeEventListener('javascriptFunction', eventFromAndroid);
        };
    }, []);

    return (<div className="App">
        <header className="App-header">
            <img src={logo} className="App-logo" alt="logo"/>
            <p>{messageFromAndroid}</p>
            <p>
                Edit <code>App.jsx</code> and save to test HMR updates.
            </p>
            <p>
                <a
                    className="App-link"
                    href="https://reactjs.org"
                    target="_blank"
                    rel="noopener noreferrer"
                >
                    Learn React
                </a>
                {' | '}
                <a
                    className="App-link"
                    href="https://vitejs.dev/guide/features.html"
                    target="_blank"
                    rel="noopener noreferrer"
                >
                    Vite Docs
                </a>
            </p>
        </header>
    </div>)
}

export default App
