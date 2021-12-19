import Login from "./components/Login/Login";
import {Navigate, Route, Routes} from "react-router-dom";
import TodoList from "./components/Todo/TodoList";
import {useContext} from "react";
import AuthenticationContext from "./store/AuthenticationContext";

function App() {

    const {authenticate} = useContext(AuthenticationContext);

    return (
        <Routes>
            <Route path="/" element={<Navigate to="/login"/>}/>
            <Route path="/login" element={<Login/>}/>
            <Route path="/todo" element={authenticate ? <TodoList/> : <Navigate to="/login"/>}/>
        </Routes>
    );
}

export default App;
