var CACHE_NAME = 'serviceworker-ex';
var CACHE_VERSION = 9;

var filesToCache = [
	"/The/TheLogin.do",
	"/Today/TodayMain.do"
];

self.oninstall = function(event) 
{
	console.info("Service worker: oninstall called");
	
	event.waitUntil(
			caches.open(CACHE_NAME + '-v' + CACHE_VERSION).then(function(cache) 
					{
						return cache.addAll(filesToCache);
					})
	);
};

self.onactivate = function(event) 
{
	console.info("Service worker: onactive called");
  
	var currentCacheName = CACHE_NAME + '-v' + CACHE_VERSION;
  
	caches.keys().then(function(cacheNames) 
			{
				return Promise.all(
						cacheNames.map(function(cacheName) 
						{
							if (cacheName.indexOf(CACHE_NAME) == -1) 
							{
								return;
							}

					        if (cacheName != currentCacheName) 
					        {
					        	return caches.delete(cacheName);
					        }
						})
				);
			});
};

self.onfetch = function(event) 
{
	var request = event.request;
	console.debug("Service worker: onfetch called" + request.url);
  
	event.respondWith(
			caches.match(request).then(function(response) 
					{
						if (response) 
						{
							return response;
						}
						return fetch(request).then(function(response) 
								{
									var responseToCache = response.clone();
									caches.open(CACHE_NAME + '-v' + CACHE_VERSION).then(
											function(cache) 
											{
												cache.put(request, responseToCache)
												.catch(function(err) 
												{
													console.info("Unable to cache:" + request.url + ': ' + err.message);
												});
											});
									return response;
								});
					})
	);
};


// Communicate via MessageChannel.
self.addEventListener('message', function(event) 
		{
			console.info(`Received message from main thread: ${event.data}`);
			event.ports[0].postMessage(`Got message! Sending direct message back - "${event.data}"`);
		});

// Broadcast via postMessage.
function sendMessage(message) 
{
	self.clients.matchAll().then(function(clients) 
			{
				clients.map(function(client) 
						{
							return client.postMessage(message);
						})
			});
}