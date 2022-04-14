import { fireEvent, render, screen, waitFor } from '@testing-library/react'

import axios from 'axios'

import App from '../App'

const mockIntersectionObserver = class {
    constructor(callback, options) {
        this.viewPort = options.root ? options.root : window
        this.observable = []
        this.viewPort.addEventListener('scroll', () => {
            this.observable.map((ob) => {
                ob.isIntersecting = this.isInViewPort(ob.target)
            })
            callback(this.observable, this)
        })
    }

    isInViewPort(target) {
        const rect = target.getBoundingClientRect()
        const viewPortRect = this.viewPort.getBoundingClientRect()
        return (
            rect.left >= viewPortRect.x &&
            rect.top >= viewPortRect.y &&
            rect.right <= viewPortRect.right &&
            rect.bottom <= viewPortRect.bottom
        )
    }

    observe(target) {
        this.observable.push({ isIntersecting: false, target })
    }

    unobserve(target) {
        this.observable = this.observable.map((ob) => ob.target !== target)
    }

    disconnect() {
        this.observable = []
    }
}

window.IntersectionObserver = mockIntersectionObserver

describe('Intersection Observer', () => {
    it('when scroll down then fetch data', async () => {
        const spyAxios = jest.spyOn(axios, 'get').mockResolvedValueOnce({
            data: {
                results: [
                    { name: '1' },
                    { name: '2' },
                    { name: '3' },
                    { name: '4' },
                    { name: '5' },
                    { name: '6' },
                    { name: '7' },
                    { name: '8' },
                    { name: '9' },
                    { name: '10' },
                ],
            },
        })
        jest.spyOn(axios, 'get').mockResolvedValueOnce({
            data: {
                results: [{ name: '11' }, { name: '12' }],
            },
        })
        await waitFor(() => {
            return render(<App />)
        })

        fireEvent.scroll(document.querySelector('#region'), { y: '500px' })

        expect(await screen.findByText('11')).toBeInTheDocument()
        expect(spyAxios).toHaveBeenCalledTimes(2)
        expect(spyAxios).toHaveBeenNthCalledWith(2, 'https://pokeapi.co/api/v2/pokemon/?offset=1&limit=20')
    })
})
