#!/usr/bin/env bash
source ./compose-utils.sh
echo "################################################################"
echo "#                                                              #"
echo "#  This script will start up ims authn kappa mock and service  #"
echo "#  the integration tests found in this project against it      #"
echo "#                                                              #"
echo "################################################################"

function startCompose {
  export imsAuthKappa_service_image=$serviceImage
  export imsAuthKappa_mock_image=$mockImage
  echo "Starting containers $serviceImage $mockImage"
  echo "Starting docker containers with docker-compose up"
  docker-compose up -d

}

function runTests {
    SERVICE_IP=$(getComposeIpAddress ims-authn-kappa)
    echo "http://${SERVICE_IP}:9010/ims-authn-kappa/v2"
    ./gradlew clean test -Dtest.config.file=localhost.properties -Dapi.endpoint.url=http://${SERVICE_IP}:9010/ims-authn-kappa/v2
}

function runTestsLocal {
    SERVICE_IP=$(getComposeIpAddress ims-authn-kappa)
    echo "http://${SERVICE_IP}:9010/ims-authn-kappa/v2"
    ./gradlew clean test -Dtest.config.file=localhost.properties
}

function cleanup {
  echo "Cleaning up docker compose environment..."
  docker-compose logs > compose.log
  docker-compose stop
  docker-compose rm -f
}

function usage {
  echo "Run with one argument: one of 'test', 'testlocal', 'up' or 'rm'"
}

case "$1" in
  test)
	runTests
	;;
  testlocal)
	runTestsLocal
    ;;
  up)
    serviceImage=$2
    mockImage=$3
	startCompose
	;;
  rm)
	cleanup
	;;
  *)
	usage
	exit 1
	;;
esac