import {useEffect, useMemo, useState} from 'react'

// 테스트 코드에서 사용하는 viewPort 입니다.
let testViewPort: HTMLElement | null

// 테스트 용 viewPort를 반환할 때 존재하지 않으면 body 를 반환합니다.
export const getTestViewPort = () => {
    return testViewPort ? testViewPort : document.body
}

export default () => {

    // 클라이언트가 사용하는 viewPort, targetRef, isInView 는 스테이트(state)로 관리합니다.
    const [viewPort, setViewPort] = useState<any>(null)
    const [targetRef, setTargetRef] = useState<any>(null)
    const [isInView, setInView] = useState<boolean>(false)

    // InterSection Observer 객체를 생성합니다.
    // useMemo 훅을 사용하여 재사용하며 viewPort가 바뀌었을 때만 재정의합니다.
    const intersectionObserver = useMemo<IntersectionObserver>(() => new IntersectionObserver((entries, observer) => {
        // 반복문을 사용해 관찰 대상자들의 상태를 확인합니다.
        entries.forEach((entry, index) => {
            // 관찰 대상자가 view port 내부에 진입했을 때
            if (entry.isIntersecting) {
                // 관찰 대상자가 view port 내부에 진입했음을 상태를 변경하여 알려줍니다.
                setInView(true)
                // 관찰 대상자를 제거합니다.
                observer.unobserve(entry.target)
            }
        })
    }, {
        // 외부에서 지정해준 view port를 사용하지만, 별도로 지정해주지 않았다면 document body를 사용합니다.
        root: viewPort ? viewPort : document.body,
    }), [viewPort])

    // view port 가 변경되면 테스트 용 view port도 변경합니다.
    useEffect(() => {
        testViewPort = viewPort
    }, [viewPort])

    // view 포트가 변경될 때마다 이전에 사용하는 InterSection Observer 객체를 정리합니다.
    useEffect(() => {
        return () => {
            intersectionObserver?.disconnect()
        }
    }, [viewPort])

    // 관찰 대상이 변경될 때마다 실행합니다.
    useEffect(() => {
        // 관찰 대상자가 새롭게 지정되었다면
        if (targetRef) {
            // 새로운 관찰 대상자가 view port 내부에 존재하지 않음을 상태를 변경해서 알려줍니다.
            setInView(false)
            // 새로운 관찰 대상자를 등록합니다.
            intersectionObserver.observe(targetRef)
        }
    }, [targetRef])

    // 클라이언트가 view port, target reference 를 지정할 수 있도록 setter 함수를 제공합니다.
    // 클라이언트가 관찰 대상자의 상태를 확인할 수 있도록 isInView 상태를 제공합니다.
    return {
        viewPort: setViewPort,
        targetRef: setTargetRef,
        isInView,
    }
}