import { Link } from "react-router-dom";

const Home = () => {
  return (
    <div className="home">
      <div>Home</div>
      <Link to="/user">move to user page</Link>
    </div>
  );
};

export default Home;
