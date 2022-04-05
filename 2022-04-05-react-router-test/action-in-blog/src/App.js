import './App.css'

import { useNavigate } from 'react-router-dom'

function App() {
    const navigate = useNavigate()

    const routeHandler = () => {
        navigate('/first')
    }

    return (
        <div>
            <button onClick={routeHandler}>submit</button>
        </div>
    )
}

export default App
