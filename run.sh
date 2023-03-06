#!/bin/bash

declare dc_file=docker-compose.yml

function build_apps() {
    ./mvnw clean verify
    #./mvnw -pl product-service spring-boot:build-image
    #./mvnw -pl promotion-service spring-boot:build-image
}

function start() {
    build_apps
    docker-compose -f "${dc_file}" up --build --force-recreate -d
    docker-compose -f "${dc_file}" logs -f
}

function stop() {
    docker-compose -f "${dc_file}" stop
    docker-compose -f "${dc_file}" rm -f
}

function restart() {
    stop
    sleep 3
    start
}

action="start"

if [[ "$#" != "0"  ]]
then
    action=$*
fi

eval "${action}"
