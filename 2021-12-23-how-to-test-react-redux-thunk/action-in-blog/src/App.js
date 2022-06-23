import Login from "./components/Login/Login";
import {Navigate, Route, Routes} from "react-router-dom";
import Main from "./components/Main/Main";
import {useSelector} from "react-redux";

function App() {

    const {isAuthenticate} = useSelector(state => state.authentication);

    return (
        <Routes>
            <Route path="/" element={<Navigate to="/login"/>}/>
            <Route path="/login" element={<Login/>}/>
            <Route path="/main" element={isAuthenticate ? <Main/> : <Navigate to="/login"/>}/>
        </Routes>
    );
}

export default App;
