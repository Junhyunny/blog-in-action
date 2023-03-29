import React from 'react';
import './App.css';

function App() {
    const eventHandler = (event: React.MouseEvent) => {
        event.preventDefault()
        alert(`click ${event.target}`)
    }

    return (
        <div className="App">
            <div id="element_div" className="square" onClick={eventHandler}>
                Div Element
            </div>
            <a id="element_link" href="https://google.com" className="square" onClick={eventHandler}>
                Google Link
            </a>
            <button id="element_button" className="square" onClick={eventHandler}>
                Button Element
            </button>
        </div>
    );
}

export default App;
