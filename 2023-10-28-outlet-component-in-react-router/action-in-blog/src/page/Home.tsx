import React from "react";
import { Link } from "react-router-dom";

const Home = () => {
  return (
    <div className="home">
      <div>Home</div>
      <Link to="/user">사용자 페이지</Link>
    </div>
  );
};

export default Home;
