import { useEffect, useState } from 'react'
import axios from 'axios'

function App() {
    const [items, setItems] = useState([])
    const [searchKey, setSearchKey] = useState('')

    useEffect(() => {
        axios.get('/api/items').then(({ data }) => {
            setItems(data)
        })
    }, [])

    const searchKeyHandler = ({ target: { value } }) => {
        setSearchKey(value)
    }

    return (
        <div>
            <div>
                <input type="text" placeholder="search" value={searchKey} onChange={searchKeyHandler} />
            </div>
            {items
                .filter((item) => !searchKey || item.name.includes(searchKey))
                .map((item) => (
                    <div key={item.id}>{item.name}</div>
                ))}
        </div>
    )
}

export default App
