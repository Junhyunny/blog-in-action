import { useDispatch, useSelector } from 'react-redux';
import { authenticationActions } from '../store/authentication';
import classes from './Header.module.css';

const Header = () => {

  const dispatch = useDispatch();

  const { isAuthenticated } = useSelector((state) => {
    return state.authentication;
  });

  const logoutHandler = () => {
    dispatch(authenticationActions.logout());
  };

  return (
    <header className={classes.header}>
      <h1>Redux Auth</h1>
      {isAuthenticated &&
        <nav>
          <ul>
            <li>
              <a href='/'>My Products</a>
            </li>
            <li>
              <a href='/'>My Sales</a>
            </li>
            <li>
              <button onClick={logoutHandler}>Logout</button>
            </li>
          </ul>
        </nav>
      }
    </header>
  );
};

export default Header;
