import { useState } from 'react'

const TransitionSlide = ({ onClose }) => {
    const [count, setCount] = useState(0)

    const increase = () => {
        setCount(count + 1)
    }

    const decrease = () => {
        setCount(count - 1)
    }

    return (
        <div className="slide skyblue">
            <div className="slide__close">
                <button onClick={onClose}>X</button>
            </div>
            <div className="slide__content">
                <p>{count}</p>
            </div>
            <div className="slide__buttons">
                <button onClick={increase}>+</button>
                <button onClick={decrease}>-</button>
            </div>
        </div>
    )
}

export default TransitionSlide
