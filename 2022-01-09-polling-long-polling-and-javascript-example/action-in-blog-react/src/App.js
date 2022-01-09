import {Navigate, Route, Routes} from "react-router-dom";
import SecondAuth from "./components/SecondAuth/SecondAuth";
import SecondAuthConfirm from "./components/SecondAuth/SecondAuthConfirm";
import Main from "./components/Main/Main";
import InfoPage from "./components/Info/InfoPage";
import ErrorPage from "./components/Error/ErrorPage";

function App() {
    return (
        <Routes>
            <Route path="/" element={<Navigate to="/second-auth"/>}/>
            <Route path="/second-auth" element={<SecondAuth/>}/>
            <Route path="/confirm" element={<SecondAuthConfirm/>}/>
            <Route path="/main" element={<Main/>}/>
            <Route path="/info" element={<InfoPage/>}/>
            <Route path="/error" element={<ErrorPage/>}/>
        </Routes>
    );
}

export default App;
