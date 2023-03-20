import React from 'react';
import './App.css';

const images = [
    '/assets/image-1.jpg',
    '/assets/image-2.png',
    '/assets/image-3.gif',
]

function App() {
    return (
        <div className="App">
            <div className="container">
                {images.map((image, index) =>
                    <div key={index} className="image">
                        <img src={image} alt={`image-${index}`}/>
                    </div>
                )}
            </div>
        </div>
    );
}

export default App;
