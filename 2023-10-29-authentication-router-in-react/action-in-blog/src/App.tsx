import React from "react";
import { Navigate, Route, Routes } from "react-router-dom";

import UserProvider from "./provider/UserProvider";
import AuthRouter from "./component/AuthRouter";

import Home from "./page/Home";
import UserPage from "./page/UserPage";
import AdminPage from "./page/AdminPage";

import "./App.css";

function App() {
  return (
    <UserProvider>
      <Routes>
        <Route path="/home" element={<Home />} />
        <Route path="/" element={<AuthRouter />}>
          <Route path="user" element={<UserPage />} />
        </Route>
        <Route path="/" element={<AuthRouter isAdminOnly={true} />}>
          <Route path="admin" element={<AdminPage />} />
        </Route>
        <Route index={true} element={<Navigate replace to={"/home"} />} />
      </Routes>
    </UserProvider>
  );
}

export default App;
