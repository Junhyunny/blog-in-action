import React from "react";
import { Link } from "react-router-dom";

const UserInformation = () => {
  return (
    <div className="user">
      <div>UserInformation</div>
      <Link to="/home">홈 페이지</Link>
    </div>
  );
};

export default UserInformation;
