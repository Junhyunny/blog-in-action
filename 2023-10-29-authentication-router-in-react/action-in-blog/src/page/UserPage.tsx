import { Link } from "react-router-dom";

const UserPage = () => {
  return (
    <div className="user">
      <div>User Page</div>
      <Link to="/admin">move to admin page</Link>
    </div>
  );
};

export default UserPage;
