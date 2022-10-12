export const startLoading = () => (dispatch) => {
    dispatch({type: 'START_LOADING'})
}

export const endLoading = () => (dispatch) => {
    dispatch({type: 'END_LOADING'})
}