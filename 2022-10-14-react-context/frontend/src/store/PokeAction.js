import {getPokemons} from "../repository/PokeRepository";

export const getPokemonsAction = (offset = 0) => async (dispatch) => {
    const {results} = await getPokemons(offset)
    dispatch({type: 'SET_POKEMONS', pokemons: results})
}