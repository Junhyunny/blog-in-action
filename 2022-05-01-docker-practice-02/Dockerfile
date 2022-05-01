# image which is based for this container
FROM node

# set working directory
WORKDIR /app

# first dot is a project directory on your file system, second dot is a directory on container
# relative directory with dot on container
COPY . ./

# command on container in working directory
RUN npm install

# command to start conatiner based on image
CMD ["node", "server.js"]

# docker iamge build command
# docker build .

# docker run iamges
# docker run -name {server-name-what-you-want} -p 80:80 -d {image id}