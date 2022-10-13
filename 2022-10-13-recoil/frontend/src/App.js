import './App.css';
import {useRef} from "react";

import {useRecoilState, useRecoilValue} from "recoil";
import {pokemonPageOffsetAtom} from "./recoil/PokemonAtoms";
import {pokemonsSelector} from "./recoil/PokemonSelector";

function App() {

    const [, setPokemonPageOffset] = useRecoilState(pokemonPageOffsetAtom);

    const pokemons = useRecoilValue(pokemonsSelector);

    const pageRef = useRef(null)

    const getPokemonsHandler = async () => {
        const page = pageRef.current.value;
        setPokemonPageOffset(page)
    }

    return (<div>
        <div className="title">
            <h2>Pokemons</h2>
            <input type="number" ref={pageRef} defaultValue={0}/>
            <button onClick={getPokemonsHandler}>fetch</button>
        </div>
        <ul>
            {pokemons.map((pokemon, index) => <li key={index}>{pokemon.name}</li>)}
        </ul>
    </div>);
}

export default App;
