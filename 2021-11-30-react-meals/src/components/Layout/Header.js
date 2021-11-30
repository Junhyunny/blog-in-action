import React from 'react';

import HeaderCartButton from './HeaderCartButton';

import classes from './Header.module.css';
import mealsImage from '../../assets/meals.jpg';

const Header = props => {
    return (
        <React.Fragment>
            <header className={classes.header}>
                <h1>React Meals</h1>
                <HeaderCartButton onClick={props.onShowCart}/>
            </header>
            <div className={classes['main-image']}>
                <img src={mealsImage} alt="A table full of delicious food." />
            </div>
        </React.Fragment>
    );
};

export default Header;