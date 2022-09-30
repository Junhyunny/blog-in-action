remove-containers:
	docker stop backend frontend &&\
 	docker rm backend frontend
build-images:
	docker build -t frontend ./frontend &&\
 	docker build -t backend ./backend
run-containers:
	docker run -d -p 8080:8080 --name backend --network custom-bridge backend &&\
 	docker run -d -p 80:80 --name frontend --network custom-bridge frontend
