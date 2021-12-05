import { useDispatch, useSelector } from 'react-redux';
import classes from './Auth.module.css';
import { authenticationActions } from '../store/authentication';

const Auth = () => {

  const dispatch = useDispatch();

  const { isAuthenticated } = useSelector((state) => {
    return state.authentication;
  });

  const loginHandler = (event) => {
    event.preventDefault();
    dispatch(authenticationActions.login());
  };

  return (
    <main className={classes.auth}>
      <section>
        <form onSubmit={loginHandler}>
          <div className={classes.control}>
            <label htmlFor='email'>Email</label>
            <input type='email' id='email' />
          </div>
          <div className={classes.control}>
            <label htmlFor='password'>Password</label>
            <input type='password' id='password' />
          </div>
          <button type="submit">Login</button>
        </form>
      </section>
    </main>
  );
};

export default Auth;
