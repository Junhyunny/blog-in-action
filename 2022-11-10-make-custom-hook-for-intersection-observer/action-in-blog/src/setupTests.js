// jest-dom adds custom jest matchers for asserting on DOM nodes.
// allows you to do things like:
// expect(element).toHaveTextContent(/react/i)
// learn more: https://github.com/testing-library/jest-dom
import '@testing-library/jest-dom';

global.IntersectionObserver = class IntersectionObserver {
    constructor(callback, options) {
        this.viewPort = options.root
        this.entries = []
        this.viewPort?.addEventListener('scroll', () => {
            this.entries.map((entry) => {
                entry.isIntersecting = this.isInViewPort(entry.target)
            })
            callback(this.entries, this)
        })
    }

    isInViewPort(target) {
        return true
    }

    observe(target) {
        this.entries.push({isIntersecting: true, target})
    }

    unobserve(target) {
        this.entries = this.entries.filter((ob) => ob.target !== target)
    }

    disconnect() {
        this.entries = []
    }
}