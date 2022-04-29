import './App.css'
import Slide from './components/Slide'
import { useState } from 'react'
import { CSSTransition } from 'react-transition-group'
import TransitionSlide from './components/TransitionSlide'

function App() {
    const [open, setOpen] = useState(false)
    const [transitionOpen, setTransitionOpen] = useState(false)

    return (
        <div className="wrapper">
            <div className="container">
                <div className="container__content">
                    <p>Container</p>
                </div>
                <button onClick={() => setOpen(true)}>slide</button>
                <button onClick={() => setTransitionOpen(true)}>transition slide</button>
            </div>
            {open && <Slide onClose={() => setOpen(false)} />}
            <CSSTransition in={transitionOpen} timeout={1000} classNames="slide" unmountOnExit>
                <TransitionSlide onClose={() => setTransitionOpen(false)} />
            </CSSTransition>
        </div>
    )
}

export default App
