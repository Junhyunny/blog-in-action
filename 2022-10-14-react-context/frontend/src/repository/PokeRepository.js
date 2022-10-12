import axios from "axios";

export const getPokemons = async (offset = 0) => {
    const {data} = await axios.get(`/api/v2/pokemon?limit=100&offset=${offset}`)
    return data
}
