import React, {useCallback, useEffect, useState} from 'react'
import classes from './App.module.css'
import useInViewPort from './hooks/useInViewPort'
import axios from 'axios'

interface Pokemon {
    name: string
}

function App() {

    const [offset, setOffset] = useState<number>(0)
    const [pokemons, setPokemons] = useState<Pokemon[]>([])

    // 커스텀 훅은 클라이언트가 지정할 수 있는 viewPort, targetRef, isInView 상태를 반환합니다.
    const {viewPort, targetRef, isInView} = useInViewPort()

    // offset 이 변경될 때마다 함수를 재정의합니다.
    const fetchData = useCallback(() => {
        axios.get(`https://pokeapi.co/api/v2/pokemon/?offset=${offset}&limit=10`)
            .then(({data: {results}}) => {
                setPokemons((prev) => prev.slice().concat(results))
            })
    }, [offset])

    // 마운트 되는 시점에 데이터를 조회합니다.
    useEffect(() => {
        fetchData()
    }, [])

    // offset 이 변경될 때마다 데이터를 재조회합니다.
    useEffect(() => {
        // offset이 0보다 크면 조회합니다.
        if (offset > 0) {
            fetchData()
        }
    }, [offset])

    // 타겟이 view port 내부로 진입, 새로운 타겟 지정 등으로 isInView 상태가 바뀔 때마다 오프셋을 늘려줍니다.
    useEffect(() => {
        if (isInView) {
            setOffset(prev => prev + 1)
        }
    }, [isInView])

    return (
        // 가장 외부 div 를 view port 로 지정합니다.
        <div ref={viewPort} className={classes.viewPort}>
            {pokemons.map((pokemon, index) => (
                // 리스트를 렌더링할 때 마지막 인덱스에 해당하는 div 를 타겟으로 지정합니다.
                <div key={index} className={classes.box} ref={pokemons.length - 1 === index ? targetRef : null}>
                    {pokemon.name}
                </div>
            ))}
        </div>
    )
}

export default App
