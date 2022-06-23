import {useEffect, useState} from "react";
import {addClickEvent, removeClickEvent} from "./EventAPI";

function CustomButton(props) {

    // useEffect(() => {
    //     console.log('componentDidMount')
    // }, [])

    // useEffect(() => {
    //     console.log('componentDidUpdate')
    // })

    // useEffect(() => {
    //     console.log('componentDidMount')
    //     return function componentWillUnmount() {
    //         console.log('componentWillUnmount')
    //     }
    // }, [])

    useEffect(() => {
        addClickEvent()
        return function cleanup() {
            removeClickEvent()
        }
    }, [])

    return (
        <button onClick={props.increaseCount}>
            Custom Click me
        </button>
    );
}

function App() {

    const [count, setCount] = useState(0)

    // useEffect(() => {
    //     console.log('componentDidUpdate count: ' + count)
    //     return function cleanup() {
    //         console.log('cleanup count: ' + count)
    //     }
    // })

    const increaseCount = () => {
        setCount(count + 1)
    }

    return (
        <div>
            <p>You clicked {count} times</p>
            {count % 2 === 1 && <CustomButton increaseCount={increaseCount}/>}
            {count % 2 === 0 &&
                <button onClick={increaseCount}>
                    Click me
                </button>
            }
        </div>
    );
}

export default App;
