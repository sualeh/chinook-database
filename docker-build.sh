#!/bin/bash
set -e

CHINOOK_DATABASE_VERSION=2.1.1

echo "** Creating Chinook Database v$CHINOOK_DATABASE_VERSION Docker container"

# Print Docker version
pwd
docker version

# Build Docker image
docker build -t schemacrawler/chinook-database .
docker tag schemacrawler/chinook-database schemacrawler/chinook-database:v$CHINOOK_DATABASE_VERSION
docker tag schemacrawler/chinook-database schemacrawler/chinook-database:latest

# Deploy image to Docker Hub
echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin
docker push schemacrawler/chinook-database
docker logout
