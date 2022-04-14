import { useCallback, useEffect, useState } from 'react'
import classes from './InfiniteScroll.module.css'
import axios from 'axios'

let intersectionObserver
let offset = 0

export default () => {
    const [pokemons, setPokemons] = useState([])

    const fetchesData = useCallback(async () => {
        const { data } = await axios.get(`https://pokeapi.co/api/v2/pokemon/?offset=${offset}&limit=20`)
        const results = data.results
        if (results.length) {
            results[results.length - 1].isLastItem = true
        }
        offset++
        setPokemons((prevState) => {
            if (prevState.length) {
                prevState[prevState.length - 1].isLastItem = false
            }
            return [].concat(prevState).concat(results)
        })
    }, [])

    useEffect(async () => {
        await fetchesData()
    }, [])

    useEffect(() => {
        intersectionObserver = new IntersectionObserver(
            (entries, observer) => {
                entries.forEach(async (entry) => {
                    if (!entry.isIntersecting) {
                        return
                    }
                    observer.unobserve(entry.target)
                    await fetchesData()
                })
            },
            {
                root: document.querySelector('#viewPort'),
            }
        )
        return () => {
            intersectionObserver.disconnect()
        }
    }, [])

    useEffect(() => {
        const lastItem = document.querySelector('.last-pokemon')
        if (lastItem) {
            intersectionObserver.observe(lastItem)
        }
    }, [pokemons])

    return (
        <div id={'viewPort'} className={classes.viewPort}>
            {pokemons.map((pokemon, index) => (
                <div key={index} className={`${classes.box} ${pokemon.isLastItem ? 'last-pokemon' : ''}`}>
                    {pokemon.name}
                </div>
            ))}
        </div>
    )
}
