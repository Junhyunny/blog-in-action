import reactDom from "react-dom";
import './BaseModalPortal.css';

const BaseModalPortal = (props) => {
    const {children, show} = props
    if (!show) {
        return <></>
    }
    return <>
        {reactDom.createPortal(<div className="backdrop"></div>, document.getElementById('backdrop'))}
        {reactDom.createPortal(children, document.getElementById("modal"))}
    </>
};


export default BaseModalPortal;