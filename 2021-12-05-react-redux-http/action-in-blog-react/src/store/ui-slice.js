import { createSlice } from "@reduxjs/toolkit";

const initialState = {
    cartIsVisible: false,
    notification: null
};

const uiSlice = createSlice({
    name: "UI",
    initialState,
    reducers: {
        toggle(state) {
            state.cartIsVisible = !state.cartIsVisible;
        },
        showNotification(state, action) {
            state.notification = {
                status: action.payload.status,
                title: action.payload.title,
                message: action.payload.message
            }
        }
    }
});

export const uiActions = uiSlice.actions;
export default uiSlice;