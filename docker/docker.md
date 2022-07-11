# Docker

Table of contents:

- [Docker images](#docker-images)
- [Docker containers](#docker-containers)
- [Port mapping](#port-mapping)
- [Logging a container](#logging-a-container)
- [Run container with a command](#run-container-with-a-command)
- [Create custom container](#create-custom-container)
- [Create docker network for comunicating containers](#create-docker-network-for-comunicating-containers)
- [Docker compose](#docker-compose)
- [Volumes](#volumes)
- [Environtments and hot reload](#environtments-and-hot-reload)

## Docker images

Command for visualizing all images installed:

```sh
docker images
```

Command for pulling images and removing them:

```sh
# Latest
docker pull node

# Version 18
docker pull node:18

# Specify platform
docker pull --platform linux/arm64 mysql

# Remove image
docker image rm node:latest
```

## Docker containers

Create a mongo container and manage it:

```sh
# Download mongo image
docker pull mongo

# Create container
docker container create mongo
docker create mongo
    returns ID

# Run container
docker start ID
    returns ID

# List running containers
docker ps

# Stop container
docker stop ID

# List all containers
docker ps -a

# CONTAINER NAME COULD BE USED INSTEAD ID

# Create container with name
docker create --name myContainer mongo
```

## Port mapping

Map ports of the host to applications inside containers:

```sh
# Create a container with mapped ports -pEXTERNALPORT:CONTAINERPORT
docker create -p27017:27017 --name mongoContainer mongo
```

## Logging a container

Show logs from a container:

```sh
# Print logs
docker logs mongoContainer

# Watch logs and wait for new entries
docker logs --follow mongoContainer
```

## Run container with a command

```sh
# Run mongo container
docker run mongo

# Run mongo container and detach
docker run -d mongo

# Run mongo with all params
docker run --name mongocontainer -p27027:27027 -d mongo
```

## Create custom container

Create Dockerfile on root folder of sources:

```dockerfile
# Custom image based on node 18 image
FROM node:18

# Create directory inside container
RUN mkdir -p /home/app

# Copy from source directory on host to container
COPY . /home/app

# Open port
EXPOSE 3000

# Execution command (specify full path)
CMD ["node", "/home/app/index.js"]
```

## Create docker network for comunicating containers

```sh
# List docker networks
docker network ls

# Create network
docker network create mired

# Delete network
docker network rm mired

# Create image
docker build -t miapp:1 .

# Create mongo container on network
docker create -p27017:27017 --name mongocontainer -network mired mongo

# Create miapp container on network
docker create -p3000:3000 --name miappcontainer --network mired miapp:1

# Now can connect to mongo with url mongodb://mongocontainer:27017
```

## Docker compose

Create file docker-compose.yml:

```yaml
# Version
version: "3.9"

services:
  miappcontainer:
    # Build file from this Dockerfile
    build: .
    ports:
      - "3000:3000"
    links:
      - mongocontainer
  mongocontainer:
    image: mongo
    ports:
      - "27017:27017"
    environment:
      - MONGO_INITDB_ROOT_USERNAME=alber
      - MONGO_INITDB_ROOT_PASSWORD=password
```

Execute compose:

```sh
docker compose up

# Ctrl + C to stop
```

Clean compose images and containers:

```sh
docker compose down
```

## Volumes

Mount folders on host system. Useful when developing and working with databases.

Types:

- Anonymous
- From guest to host
- Named

```yml
# docker-compose.yml

mongocontainer:
  # Scpecify volumes used by container
  volumes:
    - mongo-data: /data/db

# Specify volumes
volumes:
  mongo-data:
```

## Environtments and hot reload

Create new Dockerfile named Dockerfile.dev

```dockerfile
# Custom image based on node 18 image
FROM node:18

# Install nodemon
RUN npm i -g nodemon

# Create directory inside container
RUN mkdir -p /home/app

# Set WORKDIR
WORKDIR /home/app

# Open port
EXPOSE 3000

# Execution command (specify full path)
CMD ["nodemon", "index.js"]
```

Create new Docker compose named docker-compose-dev.yml

```yml
# Version
version: "3.9"

services:
  miappcontainer:
    # Build file from this Dockerfile
    build:
      context: .
      dockerfile: Dockerfile.dev
    ports:
      - "3000:3000"
    links:
      - mongocontainer
    volume:
      - .:/home/app
  mongocontainer:
    image: mongo
    ports:
      - "27017:27017"
    environment:
      - MONGO_INITDB_ROOT_USERNAME=alber
      - MONGO_INITDB_ROOT_PASSWORD=password
  # Scpecify volumes used by container
    volumes:
      - mongo-data: /data/db

# Specify volumes
volumes:
  mongo-data:
```

Execute compose:

```sh
docker compose -f docker-compose-dev.yml
```