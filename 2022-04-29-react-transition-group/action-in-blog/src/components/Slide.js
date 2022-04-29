import { useEffect, useState } from 'react'

import './Slide.css'

const Slide = ({ onClose }) => {
    const [count, setCount] = useState(0)

    const increase = () => {
        setCount(count + 1)
    }

    const decrease = () => {
        setCount(count - 1)
    }

    const slideIn = () => {
        const slide = document.querySelector('.slide')
        slide.classList.add('on')
    }

    const slideOut = () => {
        const slide = document.querySelector('.slide')
        slide.classList.add('off')
        setTimeout(() => {
            slide.classList.remove('on')
            slide.classList.remove('off')
            onClose()
        }, 950)
    }

    useEffect(() => {
        slideIn()
    }, [])

    return (
        <div className="slide orange">
            <div className="slide__close">
                <button onClick={slideOut}>X</button>
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

export default Slide
