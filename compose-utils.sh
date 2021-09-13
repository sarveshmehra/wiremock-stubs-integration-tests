#!/usr/bin/env bash

function waitAndLog {
  service=$1
  logRegex=$2

  echo -n "Waiting for ${service} to start up...";

  x=0;
  timeout=5;

  while ! docker-compose logs ${service} | tee docker-compose.log | egrep "${logRegex}" 1> /dev/null
  do
    echo -n ".";
    x=$((x+1))
    sleep 60

    if [ $x -gt $timeout ]; then
      echo "Service startup timed out!"
      cleanup
      exit 1;
    fi
  done

  sleep 5;
  echo
  echo "Service ${service} started."
}

function getComposeIpAddress {

  service=$1

  docker-compose ps -q ${service} | docker inspect --format='{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' $(xargs)
}

function getComposeIpAddressGateway {

  service=$1

  docker-compose ps -q ${service} | docker inspect --format='{{range .NetworkSettings.Networks}}{{.Gateway}}{{end}}' $(xargs)
}
