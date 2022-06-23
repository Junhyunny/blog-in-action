import './App.css';
import {useState} from "react";
import axios from "axios";

function App() {

    const [sendType, setSendType] = useState('form');
    const [path, setPath] = useState('/nothing');
    const [item, setItem] = useState('');
    const [result, setResult] = useState('');

    const sendTypeChangeHandler = ({target: {value}}) => {
        setSendType(value);
    };

    const pathChangeHandler = ({target: {value}}) => {
        setPath(value);
    }

    const itemChangeHandler = ({target: {value}}) => {
        setItem(value);
    };

    const setResponse = (response) => {
        if (sendType === 'fetch') {
            console.log(response)
            setResult(response);
        } else {
            setResult(response.data + '');
        }
    }

    const setError = (error) => {
        setResult(error + '');
    }

    const fetchHandler = () => {
        fetch(`http://localhost:8080${path}`, {
            method: "POST",
            body: {
                item
            }
        })
            .then(response => response.text())
            .then(setResponse)
            .catch(setError);
    };

    const axiosHandler = () => {
        axios.post(`http://localhost:8080${path}`, {item})
            .then(setResponse)
            .catch(setError);
    };

    const submitHandler = () => {
        if (sendType === 'fetch') {
            fetchHandler();
        } else {
            axiosHandler();
        }
    };

    return (
        <div className="App">
            <select value={sendType} onChange={sendTypeChangeHandler}>
                <option value="form">form 태그</option>
                <option value="form-with-file">form 태그 (file 전송)</option>
                <option value="fetch">fetch Web API 사용</option>
                <option value="axios">axios 모듈 사용</option>
            </select>
            <select value={path} onChange={pathChangeHandler}>
                <option value="/nothing">/nothing</option>
                <option value="/model-attribute">/model-attribute</option>
                <option value="/request-param">/request-param</option>
                <option value="/request-body">/request-body</option>
                <option value="/request-body-with-multi-value-map">/request-body-with-multi-value-map</option>
            </select>
            <div className="div">
                {
                    sendType === 'form' || sendType === 'form-with-file' ? (
                        <form method="POST" action={`http://localhost:8080${path}`} className="form"
                              encType={sendType === 'form' ? '' : 'multipart/form-data'}>
                            <input type="text" name="item" placeholder="item"/>
                            {sendType === 'form-with-file' ? (<input type="file" name="file"/>) : ''}
                            <button type="submit">Submit</button>
                        </form>
                    ) : (
                        <>
                            <input type="text" name="item" placeholder="item" onChange={itemChangeHandler}
                                   value={item}/>
                            <button onClick={submitHandler}>Submit</button>
                        </>
                    )
                }
            </div>
            <div>
                {result}
            </div>
        </div>
    );
}

export default App;
