import React, {Suspense} from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';

import App from './App';
import reportWebVitals from './reportWebVitals';

import {RecoilRoot} from "recoil";
import BaseModalPortal from "./component/BaseModalPortal";
import BaseSpinner from "./component/BaseSpinner";

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(<React.StrictMode>
    <RecoilRoot>
        <Suspense fallback={<BaseModalPortal show={true}><BaseSpinner/></BaseModalPortal>}>
            <App/>
        </Suspense>
    </RecoilRoot>
</React.StrictMode>);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
