import React from "react";
import { Navigate, Route, Routes } from "react-router-dom";

import MainContainer from "./container/MainContainer";
import Home from "./page/Home";
import UserInformation from "./page/UserInformation";

import "./App.css";

function App() {
  return (
    <Routes>
      <Route path="/" element={<MainContainer />}>
        <Route path="home" element={<Home />} />
        <Route path="user" element={<UserInformation />} />
      </Route>
      <Route index={true} element={<Navigate replace to={"/home"} />} />
    </Routes>
  );
}

export default App;
