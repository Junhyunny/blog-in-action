import './App.css';
import {useContext, useRef} from "react";
import BaseModalPortal from "./component/BaseModalPortal";
import BaseSpinner from "./component/BaseSpinner";

import {PokeContext} from "./store/PokeProvider";
import {UIContext} from "./store/UIProvider";

import {getPokemonsAction} from "./store/PokeAction";

function App() {

    const uiContext = useContext(UIContext)
    const pokeContext = useContext(PokeContext)

    const {isLoading} = uiContext.state
    const {pokemons} = pokeContext.state

    const pageRef = useRef(null)

    const getPokemonsHandler = () => {
        const page = pageRef.current.value;
        uiContext.dispatch({type: 'START_LOADING'})
        pokeContext
            .dispatch(getPokemonsAction(page))
            .finally(() => uiContext.dispatch({type: 'END_LOADING'}))
    }

    return (
        <div>
            <BaseModalPortal show={isLoading}>
                <BaseSpinner/>
            </BaseModalPortal>
            <div className="title">
                <h2>Pokemons</h2>
                <input type="number" ref={pageRef} defaultValue={0}/>
                <button onClick={getPokemonsHandler}>fetch</button>
            </div>
            <ul>
                {pokemons.map((pokemon, index) => <li key={index}>{pokemon.name}</li>)}
            </ul>
        </div>
    );
}

export default App;
