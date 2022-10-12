import './BaseSpinner.css'

const BaseSpinner = () => {
    return <div className="spinner-wrapper">
        <div className="spinner">
            <div className="lds-roller">
                <div></div>
                <div></div>
                <div></div>
                <div></div>
                <div></div>
                <div></div>
                <div></div>
                <div></div>
            </div>
        </div>
    </div>
}

export default BaseSpinner