import { createSlice } from '@reduxjs/toolkit';

const initialCounterState = {
    count: 0,
    showCount: false
};

// createSlice 기능을 사용하면 immutable state를 전달받기 때문에 직접 수저잉 가능하다.
const counterSlice = createSlice({
    name: 'counter',
    initialState: initialCounterState,
    reducers: {
        increment(state, action) {
            state.count = state.count + action.payload.amount;
        },
        decrement(state, action) {
            state.count = state.count - action.payload.amount;
        },
        toggleCounter(state) {
            state.showCount = !state.showCount
        }
    }
});

export const counterActions = counterSlice.actions;
export default counterSlice;