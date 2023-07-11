import React from "react";
import "./App.css";
import { Navigate, Route, Routes } from "react-router-dom";
import PageFirst from "./PageFirst";
import PageSecond from "./PageSecond";

function App() {
  return (
    <Routes>
      <Route path={"/"} element={<Navigate replace to="/first" />}></Route>
      <Route path={"/first"} element={<PageFirst />}></Route>
      <Route path={"/second"} element={<PageSecond />}></Route>
    </Routes>
  );
}

export default App;
