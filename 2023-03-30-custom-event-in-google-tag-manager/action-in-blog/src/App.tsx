import React from 'react';
import './App.css';

function App() {
    const eventHandler = () => {
        alert('CLICK!')
        window.dataLayer.push({event: 'custom_event', customData: `custom_event_data_${Math.random()}`})
    }

    return (
        <div className="App">
            <button className="square" onClick={eventHandler}>
                Send Custom Event
            </button>
        </div>
    );
}

export default App;
