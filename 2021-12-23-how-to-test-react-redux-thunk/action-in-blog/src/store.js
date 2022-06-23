
import {applyMiddleware, createStore} from "redux";
import ReduxThunk from 'redux-thunk';
import modules from './modules';

const store = createStore(modules, applyMiddleware(ReduxThunk));

export default store;