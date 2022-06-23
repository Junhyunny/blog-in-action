
const clickHandler = () => {
    console.log('clickHandler')
}

export const addClickEvent = () => {
    window.addEventListener('click', clickHandler)
}

export const removeClickEvent = () => {
    window.removeEventListener('click', clickHandler)
}
