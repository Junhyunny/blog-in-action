self.addEventListener('install', event => {
    console.log('[Service Worker] install');
});

self.addEventListener('activate', event => {
    console.log('[Service Worker] activate');
    clients.claim();
});

self.addEventListener('fetch', fetchHandler);

function fetchHandler(event) {
    const { request } = event;
    console.log("[Service Worker] fetch ", request);
    event.respondWith(
        caches.match(request)
            .then(response => {
                return response || fetchAndCaching(request);
            })
    );
}

function fetchAndCaching(request) {
    return fetch(request)
        .then(response =>
            caches.open('my-cache')
                .then(cache => {
                    cache.put(request, response.clone());
                    return response;
                })
                .catch(error => console.log(error))
        );
}
