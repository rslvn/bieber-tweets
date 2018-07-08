#!/usr/bin/env bash
user_home=${HOME}
current_location=$(pwd)
image_name="interview/bieber-tweets"
image_version="1.0-SNAPSHOT"
container_name="bieber-tweets"

# remove old container
docker rm -f ${container_name}

# remove old output files before test codes
rm -rf ./output/*.txt

# compile code, run unit tests and create docker image
mvn clean package docker:build

# remove old output files
rm -rf ./output/*.txt

# run the docker container
docker run -i -t \
                -v "${current_location}:/data" \
                -v "${user_home}/.m2:/root/.m2" \
                --name ${container_name} \
                ${image_name}:${image_version}