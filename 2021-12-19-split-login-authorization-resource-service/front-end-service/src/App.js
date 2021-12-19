import Login from "./components/Login/Login";
import {Navigate, Route, Routes} from "react-router-dom";
import TodoList from "./components/Todo/TodoList";

function App() {
    return (
        <Routes>
            <Route path="/login" element={<Login/>}/>
            <Route path="/todo" element={<TodoList/>}/>
            <Route path="/" element={<Navigate to="/login"/>}/>
        </Routes>
    );
}

export default App;
