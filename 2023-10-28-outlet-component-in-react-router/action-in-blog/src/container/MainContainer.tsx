import React from "react";
import { Outlet } from "react-router-dom";

const MainContainer = () => {
  return (
    <div className="main-container">
      <header>Header</header>
      <div>
        <Outlet />
      </div>
      <footer>Footer</footer>
    </div>
  );
};

export default MainContainer;
