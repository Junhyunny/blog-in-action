declare global {
    interface Window {
        dataLayer: {
            push: (data: any) => void
        }
    }
}

export {}