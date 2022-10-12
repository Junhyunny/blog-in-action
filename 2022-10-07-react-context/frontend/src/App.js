import './App.css';
import {useContext, useRef} from "react";
import BaseModalPortal from "./component/BaseModalPortal";
import BaseSpinner from "./component/BaseSpinner";

import {PokeContext} from "./store/PokeProvider";
import {UIContext} from "./store/UIProvider";

import {getPokemonsAction} from "./store/PokeAction";
import {endLoading, startLoading} from "./store/UIAction";

function App() {

    const uiContext = useContext(UIContext)
    const pokeContext = useContext(PokeContext)

    const pageRef = useRef(null)

    const getPokemonsHandler = async () => {
        const page = pageRef.current.value;
        uiContext.dispatch(startLoading())
        try {
            await pokeContext.dispatch(getPokemonsAction(page))
        } finally {
            setTimeout(() => {
                uiContext.dispatch(endLoading())
            }, 1000)
        }
    }

    return (<div>
        <BaseModalPortal show={uiContext.state.isLoading}>
            <BaseSpinner/>
        </BaseModalPortal>
        <div className="title">
            <h2>Pokemons</h2>
            <input type="number" ref={pageRef} defaultValue={0}/>
            <button onClick={getPokemonsHandler}>fetch</button>
        </div>
        <ul>
            {pokeContext.state.pokemons.map((pokemon, index) => <li key={index}>{pokemon.name}</li>)}
        </ul>
    </div>);
}

export default App;
