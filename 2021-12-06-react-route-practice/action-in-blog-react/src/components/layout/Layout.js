import { Fragment } from 'react/cjs/react.production.min';
import MainNavigation from './MainNavigation';
import classes from './Layout.module.css';

const Layout = (props) => {
    return (
        <Fragment>
            <MainNavigation />
            <main className={classes.main}>
                {props.children}
            </main>
        </Fragment>
    );
};

export default Layout;