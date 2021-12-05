import { configureStore } from '@reduxjs/toolkit';
import authenticationSlice from './authentication';
import counterSlice from './counter';

// never manipulate existing state 
// const reducer = (state = initialState, action) => {
//     if (action.type === 'INCREMENT') {
//         return {
//             count: state.count + action.amount,
//             showCount: state.showCount
//         };
//     }
//     if (action.type === 'DECREMENT') {
//         return {
//             count: state.count - action.amount,
//             showCount: state.showCount
//         };
//     }
//     if (action.type === 'TOGGLE') {
//         return {
//             count: state.count,
//             showCount: !state.showCount
//         };
//     }
//     return state;
// };

const store = configureStore({
    reducer: {
        counter: counterSlice.reducer,
        authentication: authenticationSlice.reducer
    },
});

export default store;