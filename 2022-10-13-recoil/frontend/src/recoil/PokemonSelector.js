import {selector} from "recoil";
import {pokemonPageOffsetAtom} from "./PokemonAtoms";
import {getPokemons} from "../repository/PokeRepository";

export const pokemonsSelector = selector({
    key: 'pokemonsSelector',
    get: async ({get}) => {
        const pageOffset = get(pokemonPageOffsetAtom)
        const data = await getPokemons(pageOffset)
        return data.results
    }
})