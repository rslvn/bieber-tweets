#!/usr/bin/env bash
user_home=${HOME}
current_location=$(pwd)
image_name="interview/bieber-tweets"
image_version="1.0-SNAPSHOT"
container_name="bieber-tweets"

mvn clean package docker:build
docker rm -f ${container_name}
docker run -i -t -v "${current_location}:/data" -v "${user_home}/.m2:/root/.m2" --name ${container_name} ${image_name}:${image_version}